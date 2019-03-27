package com.example.snifferservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

public class CustomRestTemplate {
    @Primary
    @Bean
    public RestTemplate getCustomRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List interceptors = restTemplate.getInterceptors();
        if(interceptors == null){
            restTemplate.setInterceptors(
                    Collections.singletonList(
                            new UserContextInterceptor()));
        } else{
            interceptors.add(new UserContextInterceptor());
            restTemplate.setInterceptors(interceptors);
        }
        return restTemplate;
    }
}
