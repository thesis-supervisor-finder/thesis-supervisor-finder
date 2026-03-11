package de.hhu.propra.thesis.domain.repository;

import de.hhu.propra.thesis.domain.model.shared.Course;
import de.hhu.propra.thesis.domain.model.shared.Topic;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface TopicRepository {
  List<Topic> findAll();
  Optional<Topic> findById(UUID id);
  Topic save(Topic topic);
  List<Topic> findRightTopics(Set<Course> courses);
}
