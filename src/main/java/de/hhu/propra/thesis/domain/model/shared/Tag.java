package de.hhu.propra.thesis.domain.model.shared;

public record Tag(String name) {
  public Tag {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("tag name must not be blank");
    }
    name = name.trim();
  }
}

