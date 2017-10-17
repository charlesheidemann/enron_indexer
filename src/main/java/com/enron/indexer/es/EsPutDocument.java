package com.enron.indexer.es;

import static com.enron.indexer.es.EsConfig.INDEX_NAME;
import static com.enron.indexer.es.EsConfig.TYPE_MESSAGE;

import java.io.IOException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enron.indexer.es.data.EsRecord;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Index;

/**
 * {@code EsPutDocument} Elasticsearch client to put a document
 */
public class EsPutDocument
{

   private static final Logger LOG = LoggerFactory.getLogger(EsPutDocument.class);

   public static boolean createDocument(final JestClient jestClient, final EsRecord esRecord)
   {

      if (Objects.isNull(esRecord))
      {
         LOG.warn("Couldn't insert null document");
         return false;
      }

      final Index request = new Index.Builder(esRecord.getJsonObject()).index(INDEX_NAME).type(TYPE_MESSAGE).id(esRecord.getId()).build();

      final JestResult jestResult;
      try
      {
         jestResult = jestClient.execute(request);
      }
      catch (IOException e)
      {
         throw new EsException(String.format("Couldn't insert document, %s", esRecord.toString()), e);
      }

      final boolean succeeded = jestResult.isSucceeded();
      if (succeeded)
      {
         LOG.info("Successfully inserted document _id {{}}", esRecord.getId());
      }
      else
      {
         LOG.warn("Couldn't insert document. Error {{}}, {{}}", jestResult.getErrorMessage(), esRecord.toString());
      }

      return succeeded;
   }
}