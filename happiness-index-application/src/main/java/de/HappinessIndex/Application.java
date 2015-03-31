package de.HappinessIndex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "de.HappinessIndex")
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        // FIXME Remove System.out
        System.out.println("###########################");
        System.out.println("Hello World!");
        System.out.println("###########################");
        SpringApplication.run(Application.class, args);
    }

}