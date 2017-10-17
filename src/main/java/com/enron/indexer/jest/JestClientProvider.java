package com.enron.indexer.jest;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;

import com.enron.indexer.Settings;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

/**
 * {@code JestClientProvider} is a factory of JestClient
 */
public class JestClientProvider
{

   private final JestClientFactory factory;

   private final CredentialsProvider credentialsProvider;

   public JestClientProvider(Settings settings)
   {
      this.factory = new JestClientFactory();
      this.credentialsProvider = new BasicCredentialsProvider();
    
      final Set<HttpHost> preemptiveAuthHosts = new HashSet<>();
      
      final String host = Optional.ofNullable(settings.getElasticsearchHost()).map(hostUri -> {
         return hostUri.toString();
      }).get();

      final HttpClientConfig.Builder httpClientConfigBuilder = new HttpClientConfig.Builder(host)
               .credentialsProvider(this.credentialsProvider)
               .connTimeout(10000)
               .readTimeout(10000)
               .maxConnectionIdleTime(10, TimeUnit.SECONDS)
               .maxTotalConnection(settings.getElasticsearchMaxConnection())
               .multiThreaded(true)
               .discoveryEnabled(false)
               .preemptiveAuthTargetHosts(preemptiveAuthHosts)
               .requestCompressionEnabled(false);

      this.factory.setHttpClientConfig(httpClientConfigBuilder.build());
   }

   public JestClient get()
   {
      return this.factory.getObject();
   }

}