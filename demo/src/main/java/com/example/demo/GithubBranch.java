package com.example.demo;

public record GithubBranch(String name, Commit commit) {
    public record Commit(String sha) {}
}
