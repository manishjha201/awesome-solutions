package com.eshop.app.config;

import com.eshop.app.common.constants.AppConstants;
import com.eshop.app.common.entities.rdbms.Catalog;
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

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("file:${config.file.path}config.properties")
@EntityScan(basePackageClasses = Catalog.class)
@EnableJpaRepositories (
        basePackages = {"com.eshop.app.common.repositories.rdbms"}, entityManagerFactoryRef = "eShopCustomEntityManagerFactory",
        transactionManagerRef = "eShopCustomTransactionManager"
)
@EnableAutoConfiguration(exclude = {DataSourceTransactionManagerAutoConfiguration.class})
public class DBConfig {
    private static final String[] ENTITY_MANAGER_PACKAGES_TO_SCAN = {"com.eshop.app.common.entities.rdbms"};

    @Value("${jdbc.driver.classname}")
    private String driverClassName;

    @Value("${primary.jdbc.url}")
    private String jdbcUrl;

    @Value("${primary.jdbc.username}")
    private String userName;

    @Value("${primary.jdbc.password}")
    private String password;

    @Value("${primary.jdbc.poolSize.min}")
    private Integer minimumIdle;

    @Value("${primary.jdbc.poolSize.max}")
    private Integer maximumPoolSize;

    @Value("${primary.jdbc.connection.timeout}")
    private Integer connectionTimeout;

    @Primary
    @Bean (name = "primaryDbConfig")
    public HikariConfig primaryDbConfig() {
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
        config.setReadOnly(false); //PRIMARY
        return config;
    }

    @Primary
    @Bean(name = "primaryDataSource")
    public DataSource mySqlDataSource(@Qualifier("primaryDbConfig") HikariConfig primaryDbConfig) {
        return new HikariDataSource(primaryDbConfig);
    }

    @Primary
    @Bean(name = "eShopCustomEntityManagerFactory")
    @PersistenceContext
    public EntityManagerFactory eShopCustomEntityManagerFactory(@Qualifier("primaryDataSource") DataSource primaryDataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapater());
        entityManagerFactoryBean.setDataSource(primaryDataSource);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(ENTITY_MANAGER_PACKAGES_TO_SCAN);
        entityManagerFactoryBean.setJpaProperties(addProperties());
        entityManagerFactoryBean.setPersistenceUnitName(AppConstants.DB_NAME);
        entityManagerFactoryBean.afterPropertiesSet();
        return entityManagerFactoryBean.getObject();
    }

    @Primary
    @Bean(name = "eShopCustomTransactionManager")
    public JpaTransactionManager eShopCustomTransactionManager(@Qualifier("eShopCustomEntityManagerFactory") EntityManagerFactory eShopCustomEntityManagerFactory) {
        return new JpaTransactionManager(eShopCustomEntityManagerFactory);
    }

    @Bean(name = "platformTransactionManager")
    public PlatformTransactionManager platformTransactionManager(@Qualifier("primaryDataSource") DataSource primaryDataSource) {
        return new DataSourceTransactionManager(primaryDataSource);
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
