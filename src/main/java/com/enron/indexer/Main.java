package com.enron.indexer;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

/**
 * {@code Main}
 */
public class Main
{

   private static final Logger LOG = LoggerFactory.getLogger(Main.class);

   public static void main(String... args)
   {
      final Settings settings = new Settings();

      final JCommander jc = new JCommander(settings);

      try
      {
         jc.parse(args);
      }
      catch (ParameterException e)
      {
         e.usage();
         System.exit(1);
      }

      LOG.info("Data import process started", settings.toString());

      LOG.info("Process {}", settings.toString());

      new Main().run(settings);
      
   }

   public void run(Settings settings)
   {
      final long startTime = System.nanoTime();

      new Enron(settings).run();

      final long elapsedTime = System.nanoTime() - startTime;

      LOG.info("Time elapsed: {{}} Seconds", TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS));
   }

}