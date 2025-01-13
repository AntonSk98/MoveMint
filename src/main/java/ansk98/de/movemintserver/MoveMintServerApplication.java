package ansk98.de.movemintserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableRetry
@EnableAsync
@EnableScheduling
@SpringBootApplication
@ConfigurationPropertiesScan
public class MoveMintServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoveMintServerApplication.class, args);
    }

}
