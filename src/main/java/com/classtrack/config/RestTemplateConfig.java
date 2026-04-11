package com.classtrack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Shared RestTemplate bean — used by StudentImageService and
 * AttendanceProcessService to call the Flask ML service.
 *
 * Add this class if you don't already have a RestTemplate @Bean defined.
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}