package com.enron.indexer.es.data;

import java.util.Objects;

import com.google.common.base.MoreObjects;
import com.google.gson.JsonObject;

/**
 * Each instance of {@code EsRecord} represents the JSON object and a given id to be put into Elasticsearch as a
 * document
 */
public class EsRecord
{

   private static final String EMPTY_STRING = "";

   private String id;

   private JsonObject jsonObject;

   public EsRecord(String id, JsonObject jsonObject)
   {
      this.id = id;
      this.jsonObject = jsonObject;
   }

   public String getId()
   {
      return id;
   }

   public JsonObject getJsonObject()
   {
      return jsonObject;
   }

   public String getJsonObjectAsString()
   {
      if (Objects.nonNull(getJsonObject()))
      {
         return getJsonObject().isJsonNull() ? EMPTY_STRING : getJsonObject().toString();
      }
      return EMPTY_STRING;
   }

   @Override
   public String toString()
   {
      return MoreObjects.toStringHelper(this)
               .add("id", getId())
               .add("jsonObject", getJsonObjectAsString())
               .toString();
   }

}
