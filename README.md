# 📦 GitHub Repo & Branch Explorer (Spring Boot)

A demo project built with **Spring Boot 3.5.4** and **Java 21**, which integrates with the GitHub REST API using **Spring Cloud OpenFeign** to list public repositories and branches of a given GitHub user — excluding forks.

---

## 🚀 Features

- ✅ List all **non-fork** public repositories for a GitHub user.
- ✅ For each repository, list all **branches** and their **latest commit SHA**.
- ✅ Custom exception handling for non-existent users.
- ✅ Integration tests using `MockWebServer` and `TestRestTemplate`.
- ✅ Clean and modular structure using records and Lombok.

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot 3.5.4
- Spring Cloud OpenFeign
- Maven
- Lombok
- JUnit 5, AssertJ,

---

## 📥 Installation

### Prerequisites

- Java 21+
- Maven 3.9+

### Build & Run

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

By default, the app runs on:  
👉 `http://localhost:8080`

---

## 🔍 API Usage

### Get all non-fork repos with branches

**Request:**

```
GET /api/v1/github/{username}/repos
```

**Example:**

```
GET http://localhost:8080/api/v1/github/octocat/repos
```

**Response:**

```json
[
  {
    "repositoryName": "Hello-World",
    "ownerLogin": "octocat",
    "branches": [
      {
        "name": "main",
        "lastCommit": "6dcb09..."
      }
    ]
  }
]
```

---

## ⚠️ Error Handling

If the user does not exist:

**Request:**
```
GET /api/v1/github/nonexistentuser/repos
```

**Response:**

```json
{
  "status": 404,
  "message": "User with username nonexistentuser not found"
}
```

---

## 🧪 Running Tests

Run integration tests using:

```bash
mvn test
```

Test coverage includes:
- Valid users with public repositories
- Users without non-fork repos
- Non-existent users (404)
- Validation of branch and commit data

---

## 🗂️ Project Structure

```
demo/
 ├── GithubController.java        # REST Controller
 ├── GithubService.java           # Business logic
 ├── GithubApi.java               # OpenFeign GitHub client
 ├── GithubFeignConfig.java       # Custom headers + error decoder
 ├── GlobalExceptionHandler.java  # Centralized exception handling
 ├── RepoDto / BranchDto          # Data Transfer Objects
 ├── IntegrationTest.java         # End-to-end integration test
 └── DemoApplication.java         # Main app entry point
```

---

## ⚙️ GitHub API Notes

This app uses GitHub's **public REST API**:
- Base URL: `https://api.github.com`
- Required headers:
  - `User-Agent`
  - `Accept: application/vnd.github+json`

No authentication token is used. If you hit the **rate limit**, you can extend this project to include OAuth or personal access tokens.

---

## 📄 License

This project is released under the **MIT License** — feel free to use and modify.

---

## ✍️ Author

Created by [Krzyskoo](https://github.com/Krzyskoo) as part of a recruitment task.
