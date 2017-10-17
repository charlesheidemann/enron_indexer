package com.enron.indexer.file.data;

import com.google.common.base.MoreObjects;

/**
 * Each instance of {@code FileLine} represents one entire line of the input file with their line number.
 */
public class FileLine
{

   private long lineNumber;

   private String lineData;

   public FileLine(long lineNumber, String lineData)
   {
      this.lineNumber = lineNumber;
      this.lineData = lineData;
   }

   public long getLineNumber()
   {
      return lineNumber;
   }

   public String getLineData()
   {
      return lineData;
   }

   @Override
   public String toString()
   {
      return MoreObjects.toStringHelper(this)
               .add("lineNumber", getLineNumber())
               .add("lineData", getLineData())
               .toString();
   }
}