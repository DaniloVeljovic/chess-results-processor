package de.dveljovic.chessresultsprocessor.semaphore;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.Semaphore;

@Component
public class SemaphoreThrottler {

    @Bean
    public Semaphore semaphore() {
        return new Semaphore(256);
    }
}
