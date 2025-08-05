package com.example.demo;

import feign.RequestInterceptor;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Configuration
public class GithubFeignConfig {

    @Bean
    public RequestInterceptor githubHeaders(

    ) {
        return requestTemplate -> {
            requestTemplate.header(HttpHeaders.ACCEPT,"application/vnd.github+json");
            requestTemplate.header(HttpHeaders.USER_AGENT,"acme-github-integration");
        };
    }
    @Bean
    public ErrorDecoder githubErrorDecoder() {
        return (methodKey, response) -> {
            int status = response.status();
            String url = response.request() != null ? response.request().url() : "";

            if (status == 404) {
                if (url.contains("/users/") && url.contains("/repos")) {
                    String username = extractBetween(url, "/users/", "/repos");
                    return new UserNotFoundException(username);
                }

            }
            return feign.FeignException.errorStatus(methodKey, response);
        };
    }
    private static String extractBetween(String text, String left, String right) {
        int i = text.indexOf(left);
        if (i < 0) return "unknown";
        int start = i + left.length();
        int j = text.indexOf(right, start);
        if (j < 0) return text.substring(start);
        return text.substring(start, j);
    }

}
