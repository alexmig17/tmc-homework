package com.epam.test.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

@Configuration
public class JacksonConfiguration {

    @Bean("upperCamelCase")
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setPropertyNamingStrategy(new UpperSnakeCaseStrategy());
        return mapper;
    }

    public static class UpperSnakeCaseStrategy extends PropertyNamingStrategy.SnakeCaseStrategy {

        @Override
        public String translate(String input) {
            if (input == null) return null;
            return super.translate(input).toUpperCase();
        }
    }
}
