package de.hhu.propra.thesis.applicationlayer.service;

import de.hhu.propra.thesis.domain.model.shared.Tag;
import de.hhu.propra.thesis.domain.model.shared.Topic;
import de.hhu.propra.thesis.domain.model.useragg.StudentProfile;
import de.hhu.propra.thesis.domain.model.useragg.User;
import de.hhu.propra.thesis.domain.repository.SupervisorProfileRepository;
import de.hhu.propra.thesis.domain.repository.TopicRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MatchingService {
  private final SupervisorProfileRepository supervisorProfileRepository;
  private final TopicRepository topicRepository;

  public MatchingService(SupervisorProfileRepository supervisorProfileRepository,
                         TopicRepository topicRepository) {
    this.supervisorProfileRepository = supervisorProfileRepository;
    this.topicRepository = topicRepository;
  }

  public List<Topic> getBestTopics(StudentProfile user) {
    Set<Tag> studentInterests = user.getInterests();

    Map<Long, Set<Tag>> supervisorInterestsMap = supervisorProfileRepository.findAll().stream()
        .collect(Collectors.toMap(s -> s.getIdentity().githubUserId(), User::getInterests));
    return topicRepository.findRightTopics(user.getPassedCourses()).stream()
        .filter(t -> supervisorInterestsMap.containsKey(t.getSupervisorId()))
        .sorted(Comparator.comparingLong((Topic t) -> {
          Set<Tag> profInterests = supervisorInterestsMap.get(t.getSupervisorId());
          return countCommonInterests(studentInterests, profInterests);
        }).reversed())
        .collect(Collectors.toList());
  }

  private long countCommonInterests(Set<Tag> studentTags, Set<Tag> supervisorTags) {
    if (studentTags == null || supervisorTags == null) {
      return 0;
    }

    return studentTags.stream()
        .filter(supervisorTags::contains)
        .count();
  }
}
