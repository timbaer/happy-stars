package de.waschnick.happy.stars;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Properties;

@SpringBootApplication
@Import(SwaggerConfig.class)
@EnableTransactionManagement
@ComponentScan(basePackages = "de.waschnick")
@EnableScheduling
public class HappyStarsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HappyStarsApplication.class, args);
    }

    @Value("${db.driver}")
    private String DB_DRIVER;

    @Value("${db.password}")
    private String DB_PASSWORD;

    @Value("${db.url}")
    private String DB_URL;

    @Value("${db.username}")
    private String DB_USERNAME;

    @PostConstruct
    public void postConstruct() {
        System.out.println("###########################");
        System.out.println("Hello World!");
        System.out.println("Make party with: " + DB_URL);
        System.out.println("###########################");
    }

    @Bean
    public DataSource dataSource() {
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

//    @Bean
//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSource());
//        sessionFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
//        Properties hibernateProperties = new Properties();
//        hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
//        hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
//        hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
//        sessionFactoryBean.setHibernateProperties(hibernateProperties);
//
//        return sessionFactoryBean;
//    }
//
//    @Bean
//    public HibernateTransactionManager transactionManager() {
//        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//        transactionManager.setSessionFactory(sessionFactory().getObject());
//        return transactionManager;
//    }

}