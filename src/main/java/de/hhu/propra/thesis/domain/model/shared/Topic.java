package de.hhu.propra.thesis.domain.model.shared;

import java.util.Set;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

public class Topic {

  @Id
  private UUID id;
  private final String title;
  private final String description;
  private final Long supervisorId;
  @MappedCollection(idColumn = "topic_id")
  private Set<Course> requirements;


  public UUID getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public Long getSupervisorId() {
    return supervisorId;
  }

  public Set<Course> getRequirements() {
    return Set.copyOf(requirements);
  }


  public Topic(UUID id, String title, String description, Long supervisorId,
               Set<Course> requirements) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.supervisorId = supervisorId;
    this.requirements = Set.copyOf(requirements);
  }
}

