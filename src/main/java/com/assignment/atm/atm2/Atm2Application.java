package com.assignment.atm.atm2;

import com.assignment.atm.atm2.service.CardServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.assignment.atm.atm2.repository")
@EntityScan("com.assignment.atm.atm2.entity")
@ComponentScan(basePackages = {"com.assignment.atm.atm2.repository", "com.assignment.atm.atm2.service", "com.assignment.atm.atm2.entity", "com.assignment.atm.atm2.controllers"})
public class Atm2Application {

    public static void main(String[] args) {
        SpringApplication.run(Atm2Application.class, args);
    }

}
