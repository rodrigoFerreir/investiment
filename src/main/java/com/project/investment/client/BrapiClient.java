package com.project.investment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.investment.client.dto.BrapiResponseDTO;

@FeignClient(name = "BrapiClient", url = "https://brapi.dev")
public interface BrapiClient {

    @GetMapping(value = "/api/quote/{codeId}")
    BrapiResponseDTO getQuote(@RequestParam("token") String token, @PathVariable("codeId") String codeId);

}
