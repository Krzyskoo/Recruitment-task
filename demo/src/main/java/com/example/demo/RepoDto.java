package com.example.demo;

import java.util.List;

public record RepoDto (String repositoryName, String ownerLogin, List<BranchDto> branches){
}
