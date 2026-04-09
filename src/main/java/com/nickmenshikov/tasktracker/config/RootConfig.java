package com.nickmenshikov.tasktracker.config;

import com.nickmenshikov.tasktracker.util.DataSourceFactory;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@Configuration
@ComponentScan(
        basePackages = "com.nickmenshikov.tasktracker",
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = RestController.class),
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class)
        }
)
public class RootConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure().load();
    }

    @Bean
    public DataSource dataSource(Dotenv dotenv) {
        return DataSourceFactory.create(
                dotenv.get("JDBC_URL"),
                dotenv.get("DB_USER"),
                dotenv.get("DB_PASSWORD")
        );
    }
}
