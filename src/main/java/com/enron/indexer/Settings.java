package com.enron.indexer;

import java.net.URI;
import java.nio.file.Path;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.PathConverter;
import com.beust.jcommander.converters.URIConverter;
import com.google.common.base.MoreObjects;

/**
 * {@code Settings} a POJO configuration to receive command line parameters
 */
public class Settings
{

   @Parameter(names = { "--input", "-i" }, description = "File to input", converter = PathConverter.class, required = true)
   private Path file;

   @Parameter(names = { "--elasticsearchHost", "-e" }, description = "Elasticsearch host", converter = URIConverter.class)
   private URI elasticsearchHost = URI.create("http://127.0.0.1:9200");

   @Parameter(names = { "--elasticsearchMaxConnection", "-c" }, description = "Max HTTP connections to Elasticsearch")
   private int elasticsearchMaxConnection = 25;

   @Parameter(names = { "--verbose", "-v" }, description = "Verbose")
   private boolean debug;

   public Path getFile()
   {
      return file;
   }

   public URI getElasticsearchHost()
   {
      return elasticsearchHost;
   }

   public int getElasticsearchMaxConnection()
   {
      return elasticsearchMaxConnection;
   }

   public boolean isDebug()
   {
      return debug;
   }

   @Override
   public String toString()
   {
      return MoreObjects.toStringHelper(this)
               .add("file", getFile())
               .add("elasticsearchHost", getElasticsearchHost())
               .add("elasticsearchMaxConnection", getElasticsearchMaxConnection())
               .add("verbose", isDebug())
               .toString();
   }

}
