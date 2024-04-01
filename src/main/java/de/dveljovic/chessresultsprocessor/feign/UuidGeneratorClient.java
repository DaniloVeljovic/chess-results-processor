package de.dveljovic.chessresultsprocessor.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "uuid-generator-client",
        url = "https://www.uuidgenerator.net/api"
)
public interface UuidGeneratorClient {

    @GetMapping(value = "/version4")
    String getUuid();
}
