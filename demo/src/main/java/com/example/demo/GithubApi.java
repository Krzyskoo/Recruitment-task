package com.example.demo;

import lombok.Getter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "github",
        url = "${github.api.base-url:https://api.github.com}"
)
public interface GithubApi {
    @GetMapping(value = "/users/{username}/repos")
    List<GithubRepo> listUserRepos(
            @PathVariable("username") String username
    );
    @GetMapping(value = "/repos/{owner}/{repo}/branches")
    List<GithubBranch> listRepoBranches(
            @PathVariable("owner") String owner,
            @PathVariable("repo") String repo
    );

}
