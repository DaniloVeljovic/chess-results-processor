package de.dveljovic.chessresultsprocessor.feign.config;

import feign.Client;
import feign.codec.ErrorDecoder;
import feign.hc5.ApacheHttp5Client;
import feign.okhttp.OkHttpClient;
import lombok.RequiredArgsConstructor;
import okhttp3.ConnectionPool;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
@EnableFeignClients(basePackages = "de.dveljovic.chessresultsprocessor.feign")
public class UuidGeneratorClientConfig {

    @Bean
    public Client feignClient() {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(10_000);
        connManager.setDefaultMaxPerRoute(10_000);
        return new ApacheHttp5Client(HttpClientBuilder.create().setConnectionManager(connManager).build());
        /*return new OkHttpClient(new okhttp3.OkHttpClient.Builder()
                .connectionPool(
                        new ConnectionPool(10_000,
                                5, TimeUnit.MINUTES))
                .build());*/
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ErrorDecoder.Default();
    }
}
