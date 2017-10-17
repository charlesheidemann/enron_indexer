package com.enron.indexer.es;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.indices.mapping.PutMapping;

/**
 * {@code EsPutMapping} is the creator of Elasticsearch Mapping
 */
public class EsPutMapping
{

   private static final Logger LOG = LoggerFactory.getLogger(EsPutMapping.class);

   public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ssZ";

   public static void createMapping(final JestClient jestClient, final String indexName, final String indexType)
   {
      
      final Map<String, Object> template = messageMapping();
      
      final PutMapping request = new PutMapping.Builder(indexName, indexType, template).build();

      final JestResult jestResult;
      try
      {
         jestResult = jestClient.execute(request);
      }
      catch (IOException e)
      {
         throw new EsException(String.format("Couldn't create mapping: %s", indexType), e);
      }

      final boolean succeeded = jestResult.isSucceeded();
      if (succeeded)
      {
         LOG.info("Successfully created mapping {{}}", indexType);
      }
      else
      {
         LOG.warn("Couldn't create mapping {{}}. Error {{}}", indexType, jestResult.getErrorMessage());
      }
   }

   private static Map<String, Boolean> enabled()
   {
      return ImmutableMap.of("enabled", true);
   }

   private static Map<String, Object> messageMapping()
   {
      return ImmutableMap.of("properties", fieldProperties(), "_source", enabled());
   }

   private static Map<String, Map<String, Object>> fieldProperties()
   {
      return ImmutableMap.<String, Map<String, Object>> builder()
               .put("sender", analyzedString("analyzer_email"))
               .put("recipients", notAnalyzedString())
               .put("cc", notAnalyzedString())
               .put("text", analyzedString("standard"))
               .put("mid", notAnalyzedString())
               .put("fpath", notAnalyzedString())
               .put("bcc", notAnalyzedString())
               .put("to", notAnalyzedString())
               .put("replyto", notAnalyzedString())
               .put("ctype", notAnalyzedString())
               .put("fname", notAnalyzedString())
               .put("date", typeTimeWithMillis())
               .put("folder", notAnalyzedString())
               .put("subject", notAnalyzedString())
               .build();
   }

   private static Map<String, Object> notAnalyzedString()
   {
      return ImmutableMap.of("index", "not_analyzed", "type", "string");
   }

   private static Map<String, Object> analyzedString(String analyzer)
   {
      return ImmutableMap.of("index", "analyzed", "type", "string", "analyzer", analyzer);
   }

   private static Map<String, Object> typeTimeWithMillis()
   {
      return ImmutableMap.of("type", "date", "format", DATE_FORMAT);
   }
}
