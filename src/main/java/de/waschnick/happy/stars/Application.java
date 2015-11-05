package de.waschnick.happy.stars;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@SpringBootApplication
@Import(SwaggerConfig.class)
@EnableTransactionManagement
@ComponentScan(basePackages = "de.waschnick")
public class Application {

    public static void main(String[] args) {
        // FIXME Remove System.out
        System.out.println("###########################");
        System.out.println("Hello World!");
        System.out.println("###########################");
        SpringApplication.run(Application.class, args);
    }

    @Value("${db.driver}")
    private String DB_DRIVER;

    @Value("${db.password}")
    private String DB_PASSWORD;

    @Value("${db.url}")
    private String DB_URL;

    @Value("${db.username}")
    private String DB_USERNAME;

    @Value("${hibernate.dialect}")
    private String HIBERNATE_DIALECT;

    @Value("${hibernate.show_sql}")
    private String HIBERNATE_SHOW_SQL;

    @Value("${hibernate.hbm2ddl.auto}")
    private String HIBERNATE_HBM2DDL_AUTO;

    @Value("${entitymanager.packagesToScan}")
    private String ENTITYMANAGER_PACKAGES_TO_SCAN;

    @Bean
    public DataSource dataSource() {
        // FIXME Remove System.out
        System.out.println("Config:");
        System.out.println("################");
        System.out.println("db.driver: " + DB_DRIVER);
        System.out.println("db.password: " + DB_PASSWORD);
        System.out.println("db.url: " + DB_URL);
        System.out.println("db.username: " + DB_USERNAME);
        System.out.println("hibernate.dialect: " + HIBERNATE_DIALECT);
        System.out.println("hibernate.show_sql: " + HIBERNATE_SHOW_SQL);
        System.out.println("hibernate.hbm2ddl.auto: " + HIBERNATE_HBM2DDL_AUTO);
        System.out.println("entitymanager.packagesToScan: " + ENTITYMANAGER_PACKAGES_TO_SCAN);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUrl(DB_URL);
        dataSource.setConnectionProperties(new Properties());

        if (DB_DRIVER.contains("postgresql")) {
            dataSource.getConnectionProperties().setProperty("ssl", "true");
            dataSource.getConnectionProperties().setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
        }
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
        hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
        hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
        sessionFactoryBean.setHibernateProperties(hibernateProperties);

        return sessionFactoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

}