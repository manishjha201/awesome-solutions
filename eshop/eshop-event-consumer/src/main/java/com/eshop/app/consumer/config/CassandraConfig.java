package com.eshop.app.consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@PropertySource("file:${config.file.path}config.properties")
@EnableCassandraRepositories(basePackages = "com.eshop.app.common.repositories.nosql")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${nosql.cassandra.keyspace.name}")
    private String keySpaceName;

    @Value("${nosql.cassandra.contactPoints}")
    private String contactPoints;

    @Value("${nosql.cassandra.port}")
    private Integer port;

    @Value("${nosql.cassandra.datacenter}")
    private String datacenter;

    @Value("${nosql.cassandra.username}")
    private String userName;

    @Value("${nosql.cassandra.password}")
    private String password;

    @Override
    protected String getKeyspaceName() {
        return keySpaceName;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    public CassandraMappingContext cassandraMapping() throws ClassNotFoundException {
        return new CassandraMappingContext();
    }

    @Primary
    @Bean
    public CqlSessionFactoryBean session() {
        CqlSessionFactoryBean session = new CqlSessionFactoryBean();
        session.setUsername(userName);
        session.setPassword(password);
        session.setLocalDatacenter(datacenter);
        session.setKeyspaceName(getKeyspaceName());
        session.setContactPoints(getContactPoints());
        session.setPort(getPort());
        return session;
    }

}
