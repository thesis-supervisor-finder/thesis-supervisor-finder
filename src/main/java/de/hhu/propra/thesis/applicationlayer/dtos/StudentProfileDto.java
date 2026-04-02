package de.hhu.propra.thesis.applicationlayer.dtos;

import java.util.Set;

public record StudentProfileDto(
    Set<String> passedCourses,
    Set<String> interests
) {
  public StudentProfileDto {
    passedCourses = passedCourses == null ? Set.of() : Set.copyOf(passedCourses);
    interests    = interests    == null ? Set.of() : Set.copyOf(interests);
  }
}
