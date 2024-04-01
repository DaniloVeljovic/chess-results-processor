package de.dveljovic.chessresultsprocessor.feign;

import de.dveljovic.chessresultsprocessor.feign.dto.GetPersonsRequest;
import de.dveljovic.chessresultsprocessor.feign.dto.GetPersonsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "fake-api-client",
        url = "https://fakerapi.it/api/v1"
)
public interface FakerApiClient {

    @GetMapping(value = "/persons")
    GetPersonsResponse getPersons(@SpringQueryMap GetPersonsRequest requestParams);
}
