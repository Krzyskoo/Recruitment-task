package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GithubService {
    private final GithubApi githubApi;

    public GithubService(GithubApi githubApi) {
        this.githubApi = githubApi;
    }

    public List<RepoDto> listNonForkReposWithBranches(String username) {
        List<GithubRepo> repos = githubApi.listUserRepos(username);
        return repos.stream()
                .filter(r -> !r.fork())
                .map(repo -> {
                    List<GithubBranch> branches = githubApi.listRepoBranches(repo.owner().login(), repo.name());
                    List<BranchDto> b = branches.stream()
                            .map(br -> new BranchDto(br.name(), br.commit().sha()))
                            .toList();
                    return new RepoDto(repo.name(), repo.owner().login(), b);
                })
                .toList();
    }
}
