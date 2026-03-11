package de.hhu.propra.thesis.domain.model.useragg;


public record GithubIdentity(long githubUserId, String login) {
  public GithubIdentity {
    if (login == null || login.isBlank()) {
      throw new IllegalArgumentException("github login must not be blank");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GithubIdentity other)) {
      return false;
    }
    return githubUserId == other.githubUserId;
  }

  @Override
  public int hashCode() {
    return Long.hashCode(githubUserId);
  }
}

