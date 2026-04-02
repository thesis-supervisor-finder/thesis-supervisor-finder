package de.hhu.propra.thesis.applicationlayer.dtos;

import java.util.Set;

public record TopicDto(
    String title,
    String descriptionMarkdown,
    Set<String> tags,
    Set<String> requiredCourses
) {

  public TopicDto {
    tags = (tags == null) ? Set.of() : Set.copyOf(tags);
    requiredCourses = (requiredCourses == null) ? Set.of() : Set.copyOf(requiredCourses);
  }
}
