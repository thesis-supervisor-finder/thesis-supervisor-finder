package de.hhu.propra.thesis.controller;

import de.hhu.propra.thesis.applicationlayer.exceptions.NoTopicFoundException;
import de.hhu.propra.thesis.applicationlayer.service.TopicService;
import de.hhu.propra.thesis.domain.model.shared.Topic;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TopicController {
  private final TopicService topicService;

  public TopicController(TopicService topicService) {
    this.topicService = topicService;
  }

  @GetMapping("/topics")
  public String getAllTopics(Model model) {
    var topics = topicService.getAllTopics();
    model.addAttribute("topics", topics);
    return "topics";
  }

  @GetMapping("/topic/{id}")
  public String getTopicDetails(@PathVariable UUID id,
                                Model model) {
    Topic topic = topicService.getTopicById(id);
    if (id == null || topic == null) {
      throw new NoTopicFoundException("There is no Topic with this id");
    }
    model.addAttribute("topic", topic);
    return "topic";
  }
}
