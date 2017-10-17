package com.enron.indexer.es;

import io.searchbox.client.JestClient;

/**
 * {@code EsConfig} is the initial configurator of Elasticsearch
 */
public class EsConfig
{

   public static final String INDEX_NAME = "enron";

   public static final String TYPE_MESSAGE = "email";

   public static void setup(final JestClient jestClient)
   {
      
      EsCreateIndex.createIndex(jestClient, INDEX_NAME);
      
      EsPutMapping.createMapping(jestClient, INDEX_NAME, TYPE_MESSAGE);
      
   }

}