package de.hhu.propra.thesis.controller;

import static de.hhu.propra.thesis.controller.StudentController.assertSelf;
import static de.hhu.propra.thesis.controller.StudentController.ifNull;
import static de.hhu.propra.thesis.controller.UserController.getInterestsSet;

import de.hhu.propra.thesis.applicationlayer.service.TopicService;
import de.hhu.propra.thesis.applicationlayer.service.UserService;
import de.hhu.propra.thesis.domain.model.shared.Course;
import de.hhu.propra.thesis.domain.model.shared.Tag;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SupervisorController {
  private final UserService supervisorService;
  private final TopicService topicService;

  public SupervisorController(UserService supervisorService, TopicService topicService) {
    this.supervisorService = supervisorService;
    this.topicService = topicService;
  }

  @GetMapping("/supervisor/{githubId}")
  public String studentInterface(@PathVariable Long githubId,
                                 @AuthenticationPrincipal OAuth2User principal,
                                 Model model) {
    assertSelf(githubId, principal);
    var supervisor = supervisorService.getSupervisorById(githubId);
    var possibleSupervisor =
        (supervisor != null) ? supervisor : supervisorService.getUserByID(githubId);
    ifNull(possibleSupervisor);
    model.addAttribute("possibleSupervisor", possibleSupervisor);
    return "supervisor";
  }

  @GetMapping("/supervisor/{githubId}/tags")
  public String editSupervisorTagsPage(@PathVariable Long githubId,
                                       @AuthenticationPrincipal OAuth2User principal,
                                       Model model) {
    assertSelf(githubId, principal);
    var supervisor = supervisorService.getSupervisorById(githubId);
    ifNull(supervisor);
    model.addAttribute("supervisor", supervisor);
    String prefill = supervisor.getInterests().stream()
        .map(Object::toString)
        .collect(Collectors.joining(", "));
    model.addAttribute("interests", prefill);
    return "supervisor-tags";
  }

  @PostMapping("/supervisor/{githubId}/tags")
  public String saveTags(@PathVariable Long githubId,
                         @AuthenticationPrincipal OAuth2User principal,
                         String newInterests,
                         Model model) {
    assertSelf(githubId, principal);
    var supervisor = supervisorService.getSupervisorById(githubId);
    ifNull(supervisor);
    if (newInterests == null || newInterests.isBlank()) {
      model.addAttribute("supervisor", supervisor);
      model.addAttribute("interestsError", "Please enter at least one tag.");
      return "supervisor-tags";
    }
    Set<Tag> interestsSet = getInterestsSet(newInterests);
    supervisorService.updateSupervisorInterests(supervisor.getIdentity(), interestsSet);
    return "redirect:/supervisor/" + githubId;
  }

  @GetMapping("/supervisor/{githubId}/topic")
  public String addTopic(@PathVariable Long githubId,
                         @AuthenticationPrincipal OAuth2User principal,
                         Model model) {
    assertSelf(githubId, principal);
    var supervisor = supervisorService.getSupervisorById(githubId);
    ifNull(supervisor);
    model.addAttribute("supervisor", supervisor);
    return "supervisor-topic";
  }

  @PostMapping("/supervisor/{githubId}/topic")
  public String saveTopic(@PathVariable Long githubId,
                          @AuthenticationPrincipal OAuth2User principal,
                          Model model, String title, String description, String requirements) {
    assertSelf(githubId, principal);
    var supervisor = supervisorService.getSupervisorById(githubId);
    ifNull(supervisor);
    Set<Course> requirementsSet = Arrays.stream(requirements.split(","))
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .map(Course::new)
        .collect(Collectors.toSet());
    boolean hasErrors = false;
    model.addAttribute("supervisor", supervisor);
    if (title.isBlank()) {
      model.addAttribute("titleError", "Should have a title");
      hasErrors = true;
    }
    if (requirementsSet.isEmpty()) {
      model.addAttribute("requirementsError", "should minimum have 1 requirement");
      hasErrors = true;
    }
    if (description.isBlank()) {
      model.addAttribute("descriptionError", "should choose a description");
      hasErrors = true;
    }
    if (hasErrors) {
      return "supervisor-topic";
    }
    topicService.createTopic(githubId, title, description, requirementsSet);
    return "redirect:/supervisor/" + githubId;
  }
}
