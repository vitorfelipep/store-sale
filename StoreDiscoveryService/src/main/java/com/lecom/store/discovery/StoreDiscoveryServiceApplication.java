package com.lecom.store.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class StoreDiscoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreDiscoveryServiceApplication.class, args);
    }

}
