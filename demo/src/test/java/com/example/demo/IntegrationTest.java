package com.example.demo;


import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "github.api.base-url=https://api.github.com"
})public class IntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnNonForkReposWithBranches_whenUserExists() {
        // Given
        String username = "Krzyskoo";
        String url = "http://localhost:" + port + "/api/v1/github/" + username + "/repos";

        // When
        RepoDto[] repos = restTemplate.getForObject(url, RepoDto[].class);

        // Then
        assertThat(repos).isNotNull();

        List<RepoDto> reposList = Arrays.asList(repos);
        System.out.println(reposList);
        assertThat(reposList).isNotEmpty();

        for (RepoDto repo : reposList) {
            assertThat(repo.repositoryName()).isNotBlank();
            assertThat(repo.ownerLogin()).isEqualTo(username);
            assertThat(repo.branches()).isNotEmpty();

            for (BranchDto branch : repo.branches()) {
                assertThat(branch.name()).isNotBlank();
                assertThat(branch.lastCommit()).isNotBlank();
            }
        }

    }
}


