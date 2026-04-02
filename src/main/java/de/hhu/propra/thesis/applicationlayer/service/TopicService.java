package de.hhu.propra.thesis.applicationlayer.service;

import de.hhu.propra.thesis.applicationlayer.exceptions.WrongInputException;
import de.hhu.propra.thesis.domain.model.shared.Course;
import de.hhu.propra.thesis.domain.model.shared.Topic;
import de.hhu.propra.thesis.domain.model.useragg.SupervisorProfile;
import de.hhu.propra.thesis.domain.repository.SupervisorProfileRepository;
import de.hhu.propra.thesis.domain.repository.TopicRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopicService {
  private final TopicRepository topicRepository;
  private final SupervisorProfileRepository supervisorProfileRepository;

  public TopicService(TopicRepository topicRepository,
                      SupervisorProfileRepository supervisorProfileRepository) {
    this.topicRepository = topicRepository;
    this.supervisorProfileRepository = supervisorProfileRepository;
  }

  @Transactional
  public void createTopic(Long githubId, String title, String description,
                          Set<Course> requirementsSet) {
    if (githubId == null || title.isEmpty() || description.isEmpty() || requirementsSet.isEmpty()) {
      throw new WrongInputException("Wrong topic details, please try again");
    }
    Optional<SupervisorProfile> possibleTopicSupervisor =
        supervisorProfileRepository.findById(githubId);
    if (possibleTopicSupervisor.isPresent()) {
      Topic newTopic = new Topic(UUID.randomUUID(), title, description, githubId, requirementsSet);
      possibleTopicSupervisor.get().addTopic(newTopic);
      topicRepository.save(newTopic);
      supervisorProfileRepository.save(possibleTopicSupervisor.get());
    }
  }

  public List<Topic> getAllTopics() {
    return topicRepository.findAll();
  }

  public Topic getTopicById(UUID id) {
    return topicRepository.findById(id).orElse(null);
  }
}
