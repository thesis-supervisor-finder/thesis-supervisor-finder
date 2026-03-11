package de.hhu.propra.thesis.domain.model.shared;

public record Course(String name) {
  public Course {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("course name must not be blank");
    }
    name = name.trim();
  }
}

