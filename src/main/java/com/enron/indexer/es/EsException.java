package com.enron.indexer.es;

/**
 * {@code EsException} is the internal {@code Exception} of those exceptions that can be thrown during the normal
 * interaction with Elasticsearch.
 */
public class EsException extends RuntimeException
{

   private static final long serialVersionUID = 5536876519810574035L;

   public EsException(String message)
   {
      super(message);
   }

   public EsException(String message, Throwable cause)
   {
      super(message, cause);
   }

}
