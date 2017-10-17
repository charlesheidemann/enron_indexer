package com.enron.indexer.es.builder;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enron.indexer.es.data.EsRecord;
import com.enron.indexer.file.data.FileLine;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * {@code EsRecordBuilder} to obtain EsRecord instance from a FileLine
 */
public class EsRecordBuilder
{

   private static final Logger LOG = LoggerFactory.getLogger(EsRecordBuilder.class);

   private static final String ID = "_id";

   private static final String OID = "$oid";

   public static EsRecord parse(FileLine fileLine)
   {
      if (Objects.isNull(fileLine))
      {
         return null;
      }
      return createESRecord(fileLine);
   }

   private static EsRecord createESRecord(FileLine fileLine)
   {
      try
      {
         final JsonElement parse = new JsonParser().parse(fileLine.getLineData());
         final JsonObject jsonObject = parse.getAsJsonObject();
         final String id = jsonObject.remove(ID).getAsJsonObject().get(OID).getAsString();
         LOG.info("Successfully parsed line {{}}, _id {{}}", fileLine.getLineNumber(), id);
         return new EsRecord(id, jsonObject);
      }
      catch (Exception e)
      {
         LOG.warn("Error is found while parsing. Error {{}}, {{}} ", e.getMessage(), fileLine.toString());
      }
      
      return null;
   }

}
