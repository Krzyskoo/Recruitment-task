package com.example.demo;

public record GithubRepo(String name, Owner owner, boolean fork) {
    public record Owner(String login) {}
}
