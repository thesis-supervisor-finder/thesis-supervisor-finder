package de.hhu.propra.thesis.applicationlayer.dtos;

import java.util.Set;

public record SupervisorProfileDto(Set<String> interests) {

  public SupervisorProfileDto {
    interests = (interests == null) ? Set.of() : Set.copyOf(interests);
  }
}
