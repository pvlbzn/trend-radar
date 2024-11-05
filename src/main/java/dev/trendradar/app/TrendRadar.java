package dev.trendradar.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication()
@EnableCaching
public class TrendRadar {
    public static void main(String[] args) {
        SpringApplication.run(TrendRadar.class, args);
    }
}
