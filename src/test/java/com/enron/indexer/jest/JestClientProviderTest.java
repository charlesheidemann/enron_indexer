package com.enron.indexer.jest;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.enron.indexer.Settings;

import io.searchbox.client.JestClient;
import io.searchbox.client.http.JestHttpClient;

public class JestClientProviderTest
{

   @Test
   public void getJestHttpClient()
   {
      final JestClientProvider provider = new JestClientProvider(new Settings());
      final JestClient jestClient = provider.get();
      assertThat(jestClient, instanceOf(JestHttpClient.class));
   }

}
