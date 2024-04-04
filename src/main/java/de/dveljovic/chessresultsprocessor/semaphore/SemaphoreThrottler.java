package de.dveljovic.chessresultsprocessor.semaphore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Semaphore;

@Configuration
public class SemaphoreThrottler {

    @Bean
    public Semaphore semaphore() {
        return new Semaphore(256);
    }
}
