package com.enron.indexer.es;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.enron.indexer.es.builder.EsRecordBuilder;
import com.enron.indexer.es.data.EsRecord;
import com.enron.indexer.file.data.FileLine;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;

@RunWith(MockitoJUnitRunner.class)
public class EsPutDocumentTest
{

   @Rule
   public ExpectedException thrown = ExpectedException.none();

   @Mock
   private JestClient clientMock;

   private EsRecord getValidEsRecord()
   {
      final String lineData = "{ \"_id\" : { \"$oid\" : \"52af48b5d55148fa0c199643\" }, \"sender\" : \"rosalee.fleming@enron.com\"}";
      final FileLine fileLine = new FileLine(1, lineData);
      final EsRecord esRecord = EsRecordBuilder.parse(fileLine);
      return esRecord;
   }

   @Test
   public void executeWithIOException() throws Exception
   {
      final EsRecord esRecord = getValidEsRecord();
      when(clientMock.execute(any())).thenThrow(IOException.class);
      thrown.expect(EsException.class);
      EsPutDocument.createDocument(clientMock, esRecord);
   }

   @Test
   public void executeWithSuccessResponse() throws Exception
   {
      final EsRecord esRecord = getValidEsRecord();
      final JestResult resultMock = mock(JestResult.class);

      when(resultMock.isSucceeded()).thenReturn(true);
      when(clientMock.execute(any())).thenReturn(resultMock);

      final boolean result = EsPutDocument.createDocument(clientMock, esRecord);

      assertTrue(result);
   }

   @Test
   public void executeWithUnSuccessResponse() throws Exception
   {
      final EsRecord esRecord = getValidEsRecord();
      final JestResult resultMock = mock(JestResult.class);

      when(resultMock.isSucceeded()).thenReturn(false);
      when(resultMock.getErrorMessage()).thenReturn("INVALID!!!");
      when(clientMock.execute(any())).thenReturn(resultMock);

      final boolean result = EsPutDocument.createDocument(clientMock, esRecord);

      assertFalse(result);
   }

}
