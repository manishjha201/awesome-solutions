package com.eshop.app.consumer.config;

import org.elasticsearch.client.RestClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


@Slf4j
@Configuration
@PropertySource("file:${config.file.path}config.properties")
@EnableElasticsearchRepositories(basePackages = "com.eshop.app.common.repositories.nosql.es")
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration  {

    @Value("${eshop.elasticsearch.host}")
    private String elasticsearchHost;

    @Value("${eshop.elasticsearch.port}")
    private int elasticsearchPort;

    @Value("${eshop.elasticsearch.username}")
    private String username;

    @Value("${eshop.elasticsearch.password}")
    private String password;

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

}
