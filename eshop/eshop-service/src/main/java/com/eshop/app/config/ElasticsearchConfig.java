package com.eshop.app.config;

<<<<<<< HEAD
import org.elasticsearch.client.RestClient;
=======

>>>>>>> 4a914d4 (stage4 : es integration)
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
<<<<<<< HEAD
import org.elasticsearch.client.RestClientBuilder;
=======
import org.elasticsearch.client.RestClient;
>>>>>>> 4a914d4 (stage4 : es integration)
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
<<<<<<< HEAD
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


=======
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

>>>>>>> 4a914d4 (stage4 : es integration)
@Slf4j
@Configuration
@PropertySource("file:${config.file.path}config.properties")
@EnableElasticsearchRepositories(basePackages = "com.eshop.app.common.repositories.nosql.es")
<<<<<<< HEAD
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration  {
=======
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
>>>>>>> 4a914d4 (stage4 : es integration)

    @Value("${eshop.elasticsearch.host}")
    private String elasticsearchHost;

    @Value("${eshop.elasticsearch.port}")
    private int elasticsearchPort;

    @Value("${eshop.elasticsearch.username}")
    private String username;

    @Value("${eshop.elasticsearch.password}")
    private String password;

<<<<<<< HEAD
    /*
=======
>>>>>>> 4a914d4 (stage4 : es integration)
    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
<<<<<<< HEAD
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                        return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });
        return new RestHighLevelClient(builder);
    }
     */
    @Override
    @Bean(destroyMethod = "close")
    public RestHighLevelClient elasticsearchClient() {
        RestClientBuilder builder = RestClient.builder(
                        new HttpHost(elasticsearchHost, elasticsearchPort))
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                    credentialsProvider.setCredentials(AuthScope.ANY,
                            new UsernamePasswordCredentials(username,
                                    password));
                    return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                });
        return new RestHighLevelClient(builder);
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }

=======
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", password));
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
                        .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
        );
    }
>>>>>>> 4a914d4 (stage4 : es integration)
}
