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
import org.springframework.context.annotation.Primary;
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
@EnableJpaRepositories (
        basePackages = {"com.eshop.app.common.repositories.rdbms.master"}, entityManagerFactoryRef = "eShopMasterEntityManagerFactory",
        transactionManagerRef = "eShopMasterTransactionManager"
)
@EnableAutoConfiguration(exclude = {DataSourceTransactionManagerAutoConfiguration.class})
public class DBConfigMaster {
    private static final String[] ENTITY_MANAGER_PACKAGES_TO_SCAN = {"com.eshop.app.common.entities.rdbms"};

    @Value("${jdbc.driver.classname}")
    private String driverClassName;

    @Value("${jdbc.master.url}")
    private String jdbcUrl;

    @Value("${jdbc.master.username}")
    private String userName;

    @Value("${jdbc.master.password}")
    private String password;

    @Value("${jdbc.master.poolSize.min}")
    private Integer minimumIdle;

    @Value("${jdbc.master.poolSize.max}")
    private Integer maximumPoolSize;

    @Value("${jdbc.master.connection.timeout}")
    private Integer connectionTimeout;

    @Primary
    @Bean (name = "masterDbConfig")
    public HikariConfig masterDbConfig() {
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
        config.setReadOnly(false); //master
        return config;
    }

    @Primary
    @Bean(name = "masterDataSource")
    public DataSource mySqlDataSource(@Qualifier("masterDbConfig") HikariConfig masterDbConfig) {
        return new HikariDataSource(masterDbConfig);
    }

    @Primary
    @Bean(name = "eShopMasterEntityManagerFactory")
    @PersistenceContext
    public LocalContainerEntityManagerFactoryBean eShopMasterEntityManagerFactory(@Qualifier("masterDataSource") DataSource masterDataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapater());
        entityManagerFactoryBean.setDataSource(masterDataSource);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(ENTITY_MANAGER_PACKAGES_TO_SCAN);
        entityManagerFactoryBean.setJpaProperties(addProperties());
        entityManagerFactoryBean.setPersistenceUnitName("master");
        entityManagerFactoryBean.afterPropertiesSet();
        return entityManagerFactoryBean;
    }

    @Primary
    @Bean(name = "eShopMasterTransactionManager")
    public JpaTransactionManager eShopCustomTransactionManager(@Qualifier("eShopMasterEntityManagerFactory") LocalContainerEntityManagerFactoryBean eShopMasterEntityManagerFactory) {
        return new JpaTransactionManager(eShopMasterEntityManagerFactory.getObject());
    }

    @Primary
    @Bean(name = "platformTransactionManager")
    public PlatformTransactionManager platformTransactionManager(@Qualifier("masterDataSource") DataSource masterDataSource) {
        return new DataSourceTransactionManager(masterDataSource);
    }

    private HibernateJpaVendorAdapter vendorAdapater() {
        return new HibernateJpaVendorAdapter();
    }

    private Properties addProperties() {
        Properties prop = new Properties();
        prop.put("spring.jpa.show-sql" , "true");
        prop.put("spring.jpa.hibernate.ddl-auto", "create-drop");
        return prop;
    }

}
