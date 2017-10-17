package com.enron.indexer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enron.indexer.es.EsConfig;
import com.enron.indexer.es.EsPutDocument;
import com.enron.indexer.es.builder.EsRecordBuilder;
import com.enron.indexer.file.data.FileLine;
import com.enron.indexer.jest.JestClientProvider;

import io.searchbox.client.JestClient;

/**
 * {@code Enron} data import process control
 */
public class Enron
{

   private static final Logger LOG = LoggerFactory.getLogger(Enron.class);

   private final Settings settings;

   private final JestClientProvider jestClientProvider;

   private final JestClient jestClient;

   public Enron(Settings settings)
   {
      this.settings = settings;
      this.jestClientProvider = new JestClientProvider(settings);
      this.jestClient = jestClientProvider.get();
   }

   private void init()
   {
      if (settings.isDebug())
      {
         Configurator.setLevel(getClass().getPackage().getName(), Level.INFO);
      }

      EsConfig.setup(jestClient);

   }

   private void stop()
   {
      Optional.ofNullable(this.jestClient).ifPresent(JestClient::shutdownClient);
   }

   private void processFile() throws IOException
   {
      final AtomicLong lineCount = new AtomicLong();
      
      try (Stream<String> lines = Files.lines(settings.getFile(), Charset.defaultCharset()))
      {
         lines.map(line -> new FileLine(lineCount.incrementAndGet(), line))
                  .parallel()
                  .map(line -> EsRecordBuilder.parse(line))
                  .forEach(record -> EsPutDocument.createDocument(jestClient, record));
      }
      
   }

   public void run()
   {
      try
      {
         init();
         processFile();
      }
      catch (Exception e)
      {
         LOG.error("Error during executing, {}", settings.toString(), e);
      }
      finally
      {
         stop();
      }
   }

}
