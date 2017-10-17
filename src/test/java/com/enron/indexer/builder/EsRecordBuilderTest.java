package com.enron.indexer.builder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.enron.indexer.es.builder.EsRecordBuilder;
import com.enron.indexer.es.data.EsRecord;
import com.enron.indexer.file.data.FileLine;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class EsRecordBuilderTest
{

   @Test
   public void executeWithSuccessParsingDataByInvalidFileLine()
   {
      final FileLine fileLine = new FileLine(1, "");
      final EsRecord build = EsRecordBuilder.parse(fileLine);
      assertNull(build);
   }

   @Test
   public void executeWithSuccessParsingDataByNullInput()
   {
      final EsRecord build = EsRecordBuilder.parse(null);
      assertNull(build);
   }

   @Test
   public void executeWithSuccessParsingDataAndGetId()
   {
      final String lineData = "{ \"_id\" : { \"$oid\" : \"52af48b5d55148fa0c199643\" }, \"sender\" : \"rosalee.fleming@enron.com\"}";
      final String oid = "52af48b5d55148fa0c199643";
      final FileLine fileLine = new FileLine(1, lineData);
      final EsRecord build = EsRecordBuilder.parse(fileLine);
      assertEquals(build.getId(), oid);
   }

   @Test
   public void executeWithSuccessRemovingIdFromJSON()
   {
      final String lineData = "{ \"_id\" : { \"$oid\" : \"52af48b5d55148fa0c199643\" }, \"sender\" : \"rosalee.fleming@enron.com\"}";
      final JsonElement parse = new JsonParser().parse(lineData);
      final FileLine fileLine = new FileLine(1, lineData);
      final EsRecord build = EsRecordBuilder.parse(fileLine);
      assertNotEquals(parse, build.getJsonObject());
      assertNull(build.getJsonObject().get("id"));
   }
}
