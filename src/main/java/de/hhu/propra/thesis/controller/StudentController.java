package de.hhu.propra.thesis.controller;

import de.hhu.propra.thesis.applicationlayer.exceptions.UserNotFoundException;
import de.hhu.propra.thesis.applicationlayer.service.MatchingService;
import de.hhu.propra.thesis.applicationlayer.service.UserService;
import de.hhu.propra.thesis.domain.model.shared.Course;
import de.hhu.propra.thesis.domain.model.shared.Tag;
import de.hhu.propra.thesis.domain.model.useragg.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;
import java.util.stream.Collectors;

import static de.hhu.propra.thesis.controller.UserController.extractGithubId;
import static de.hhu.propra.thesis.controller.UserController.getInterestsSet;

@Controller
public class StudentController {
  private final UserService studentService;
  private final MatchingService matchingService;

  public StudentController(UserService studentService, MatchingService matchingService) {
    this.studentService = studentService;
    this.matchingService = matchingService;
  }

  @GetMapping("/student/{githubId}")
  public String studentInterface(@PathVariable Long githubId,
                                 @AuthenticationPrincipal OAuth2User principal,
                                 Model model) {
    assertSelf(githubId, principal);

    var user = studentService.getStudentByID(githubId);
    ifNull(user);
    model.addAttribute("student", user);
    return "student";
  }

  @GetMapping("/student/{githubId}/tags")
  public String editTagsPage(@PathVariable Long githubId,
                             @AuthenticationPrincipal OAuth2User principal,
                             Model model) {
    assertSelf(githubId, principal);

    var user = studentService.getStudentByID(githubId);
    ifNull(user);
    model.addAttribute("student", user);

    String prefill = user.getInterests().stream()
        .map(Object::toString)
        .collect(Collectors.joining(", "));
    model.addAttribute("interests", prefill);

    return "student-tags";
  }

  @GetMapping("/student/{githubId}/courses")
  public String editPassedCourses(@PathVariable Long githubId,
                                  @AuthenticationPrincipal OAuth2User principal,
                                  Model model) {
    assertSelf(githubId, principal);

    var user = studentService.getStudentByID(githubId);
    ifNull(user);
    model.addAttribute("student", user);
    return "student-courses";
  }

  @PostMapping("/student/{githubId}/courses")
  public String savePassedCourses(@PathVariable Long githubId,
                                  @AuthenticationPrincipal OAuth2User principal,
                                  @RequestParam String passedCourse,
                                  Model model) {
    assertSelf(githubId, principal);

    var user = studentService.getStudentByID(githubId);
    ifNull(user);

    if (passedCourse == null || passedCourse.isBlank()) {
      model.addAttribute("student", user);
      model.addAttribute("coursesError", "Please enter a course.");
      return "student-courses";
    }

    Course course = new Course(passedCourse);
    studentService.updateStudentCourses(user.getIdentity(), course);
    return "redirect:/student/" + githubId;
  }

  @PostMapping("/student/{githubId}/tags")
  public String saveTags(@PathVariable Long githubId,
                         @AuthenticationPrincipal OAuth2User principal,
                         String newInterests,
                         Model model) {
    assertSelf(githubId, principal);

    var user = studentService.getStudentByID(githubId);
    ifNull(user);

    if (newInterests == null || newInterests.isBlank()) {
      model.addAttribute("student", user);
      model.addAttribute("interestsError", "Please enter at least one tag.");
      return "student-tags";
    }

    Set<Tag> interestsSet = getInterestsSet(newInterests);
    studentService.updateStudentInterests(user.getIdentity(), interestsSet);

    return "redirect:/student/" + githubId;
  }

  static void ifNull(User user) {
    if (user == null) {
      throw new UserNotFoundException("could not load user");
    }
  }

  static void assertSelf(Long pathGithubId, OAuth2User principal) {
    if (principal == null) {
      throw new AccessDeniedException("Not allowed");
    }
    Long me = extractGithubId(principal);
    if (me == null || !me.equals(pathGithubId)) {
      throw new AccessDeniedException("Not allowed");
    }
  }

  @GetMapping("/student/{githubId}/matching")
  public String getBestTopics(@AuthenticationPrincipal OAuth2User principal,
                              Model model,
                              @PathVariable Long githubId) {
    assertSelf(githubId, principal);
    var user = studentService.getStudentByID(githubId);
    ifNull(user);
    var bestTopics = matchingService.getBestTopics(user);
    model.addAttribute("student", user);
    model.addAttribute("bestTopics", bestTopics);
    return "student-matching";
  }
}
