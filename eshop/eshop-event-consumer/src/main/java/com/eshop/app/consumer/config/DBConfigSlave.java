package com.eshop.app.consumer.config;

import com.eshop.app.common.entities.rdbms.Product;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("file:${config.file.path}config.properties")
@EntityScan(basePackageClasses = Product.class)
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.eshop.app.common.repositories.rdbms.slave"}, entityManagerFactoryRef = "eShopSlaveEntityManagerFactory",
        transactionManagerRef = "eShopSlaveTransactionManager"
)
@EnableAutoConfiguration(exclude = {DataSourceTransactionManagerAutoConfiguration.class})
public class DBConfigSlave {
    private static final String[] ENTITY_MANAGER_PACKAGES_TO_SCAN = {"com.eshop.app.common.entities.rdbms"};

    @Value("${jdbc.driver.classname}")
    private String driverClassName;

    @Value("${jdbc.slave.url}")
    private String jdbcUrl;

    @Value("${jdbc.slave.username}")
    private String userName;

    @Value("${jdbc.slave.password}")
    private String password;

    @Value("${jdbc.slave.poolSize.min}")
    private Integer minimumIdle;

    @Value("${jdbc.slave.poolSize.max}")
    private Integer maximumPoolSize;

    @Value("${jdbc.slave.connection.timeout}")
    private Integer connectionTimeout;


    @Bean(name = "slaveDbConfig")
    public HikariConfig slaveDbConfig() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(userName);
        config.setPassword(password);
        config.setMinimumIdle(minimumIdle);
        config.setMaximumPoolSize(maximumPoolSize);
        config.setConnectionTimeout(connectionTimeout);
        config.setConnectionInitSql("SELECT 1");
        config.setConnectionTestQuery("SELECT 1");
        config.setLeakDetectionThreshold(1000L);
        config.setIdleTimeout(600000L);
        config.setReadOnly(false); //slave
        return config;
    }


    @Bean(name = "slaveDataSource")
    public DataSource mySqlDataSource(@Qualifier("slaveDbConfig") HikariConfig slaveDbConfig) {
        return new HikariDataSource(slaveDbConfig);
    }


    @Bean(name = "eShopSlaveEntityManagerFactory")
    @PersistenceContext
    public LocalContainerEntityManagerFactoryBean eShopSlaveEntityManagerFactory(@Qualifier("slaveDataSource") DataSource slaveDataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapater());
        entityManagerFactoryBean.setDataSource(slaveDataSource);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(ENTITY_MANAGER_PACKAGES_TO_SCAN);
        entityManagerFactoryBean.setJpaProperties(addProperties());
        entityManagerFactoryBean.setPersistenceUnitName("slave");
        entityManagerFactoryBean.afterPropertiesSet();
        return entityManagerFactoryBean;
    }


    @Bean(name = "eShopSlaveTransactionManager")
    public JpaTransactionManager eShopCustomTransactionManager(@Qualifier("eShopSlaveEntityManagerFactory") LocalContainerEntityManagerFactoryBean eShopSlaveEntityManagerFactory) {
        return new JpaTransactionManager(eShopSlaveEntityManagerFactory.getObject());
    }


    @Bean(name = "platformTransactionManager")
    public PlatformTransactionManager platformTransactionManager(@Qualifier("slaveDataSource") DataSource slaveDataSource) {
        return new DataSourceTransactionManager(slaveDataSource);
    }

    private HibernateJpaVendorAdapter vendorAdapater() {
        return new HibernateJpaVendorAdapter();
    }

    private Properties addProperties() {
        Properties prop = new Properties();
        prop.put("spring.jpa.show-sql" , "true");
        return prop;
    }

}
