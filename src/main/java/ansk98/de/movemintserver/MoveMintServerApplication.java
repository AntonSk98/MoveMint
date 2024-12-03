package ansk98.de.movemintserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MoveMintServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoveMintServerApplication.class, args);
    }

}
