package com.nickmenshikov.flux.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.nickmenshikov.flux")
@EnableJpaRepositories("com.nickmenshikov.flux")
@EntityScan("com.nickmenshikov.flux.core.model")
public class FluxApplication {
    static void main(String[] args) {
        SpringApplication.run(FluxApplication.class, args);
    }
}
