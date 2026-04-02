package de.hhu.propra.thesis.infrastructurelayer.reposimplementation;

import de.hhu.propra.thesis.domain.model.shared.Course;
import de.hhu.propra.thesis.domain.model.shared.Topic;
import de.hhu.propra.thesis.domain.repository.TopicRepository;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public interface SqlTopicRepository extends CrudRepository<Topic, UUID>, TopicRepository {

  @Override
  @Query("SELECT * FROM topic")
  List<Topic> findAll();

  @Query("SELECT DISTINCT t.* FROM topic t " +
      "JOIN topic_requirements tr ON t.id = tr.topic_id " +
      "WHERE tr.name IN (:courseNames)")
  List<Topic> internalFindRightTopics(@Param("courseNames") Set<String> courseNames);

  @Override
  default List<Topic> findRightTopics(Set<Course> courses) {
    if (courses == null || courses.isEmpty()) {
      return List.of();
    }
    Set<String> courseNames = courses.stream()
        .map(Course::name)
        .collect(Collectors.toSet());

    return internalFindRightTopics(courseNames);
  }
}