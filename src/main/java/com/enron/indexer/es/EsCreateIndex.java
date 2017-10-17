package com.enron.indexer.es;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.indices.CreateIndex;

/**
 * {@code EsCreateIndex} is the creator of Elasticsearch Index
 */
public class EsCreateIndex
{

   private static final Logger LOG = LoggerFactory.getLogger(EsCreateIndex.class);

   public static void createIndex(final JestClient jestClient, final String indexName)
   {

      final Map<String, Object> tokenizerEmail = ImmutableMap.of("tokenizer_email", ImmutableMap.of("type", "uax_url_email", "max_token_length", 4));

      final Map<String, Object> analyzerEmail = ImmutableMap.of("tokenizer", "tokenizer_email", "filter", "lowercase");

      final Map<String, Object> analyzer = ImmutableMap.of("analyzer_email", analyzerEmail);

      final Map<String, Object> analysis = ImmutableMap.of("analyzer", analyzer, "tokenizer", tokenizerEmail);

      final Map<String, Object> settings = ImmutableMap.of("number_of_shards", 4, "number_of_replicas", 0, "analysis", analysis);

      final CreateIndex request = new CreateIndex.Builder(indexName).settings(settings).build();

      final JestResult jestResult;
      try
      {
         jestResult = jestClient.execute(request);
      }
      catch (IOException e)
      {
         throw new EsException(String.format("Couldn't create index: %s", indexName), e);
      }

      final boolean succeeded = jestResult.isSucceeded();
      if (succeeded)
      {
         LOG.info("Successfully created index: {{}}", indexName);
      }
      else
      {
         LOG.warn("Couldn't create index {{}}. Error: {}", indexName, jestResult.getErrorMessage());
      }

   }

}