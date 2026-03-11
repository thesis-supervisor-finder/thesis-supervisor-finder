package de.hhu.propra.thesis.controller;

import de.hhu.propra.thesis.applicationlayer.exceptions.UserNotFoundException;
import de.hhu.propra.thesis.applicationlayer.service.UserService;
import de.hhu.propra.thesis.domain.model.shared.Tag;
import de.hhu.propra.thesis.domain.model.useragg.GithubIdentity;
import de.hhu.propra.thesis.domain.model.useragg.Role;
import de.hhu.propra.thesis.domain.model.useragg.User;
import de.hhu.propra.thesis.domain.model.util.EmailValidator;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/")
  public String index() {
    return "index";
  }


  @GetMapping("/user")
  public String userInterface(Authentication auth, RedirectAttributes flashModel,
                              Model model) {
    String name = auth.getName();
    flashModel.addFlashAttribute("adminName", name);
    if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
      return "redirect:/admin";
    }
    model.addAttribute("username", name);
    return "user";
  }

  @PostMapping("/user")
  public String userInterfacePost(@AuthenticationPrincipal OAuth2User user,
                                  Model model, String email, String interests, String roleChoice) {
    Long githubId = extractGithubId(user);
    if (githubId == null) {
      throw new UserNotFoundException("fail to load user");
    } else {
      User userByID = userService.getUserByID(githubId);
      if (userByID != null) {
        String path = userByID.getRole().equals(Role.SUPERVISOR) ? "supervisor/" : "student/";
        return "redirect:/" + path + githubId;
      }
    }
    boolean hasErrors = false;
    if (!EmailValidator.isValidEmail(email)) {
      model.addAttribute("emailError", "Invalid email");
      hasErrors = true;
    }
    if (interests == null) {
      model.addAttribute("interestsError", "should minimum have 1 interest");
      hasErrors = true;
    }
    if (roleChoice == null) {
      model.addAttribute("roleError", "should choose a role");
      hasErrors = true;
    }
    if (hasErrors) {
      model.addAttribute("username", user.getAttribute("login"));
      return "user";
    }
    Set<Tag> interestsSet = getInterestsSet(interests);
    String githubLogin = user.getAttribute("login");
    if (githubLogin != null) {
      GithubIdentity identity = new GithubIdentity(githubId, githubLogin);
      if (roleChoice.equals("STUDENT")) {
        userService.createStudentProfile(identity, email, interestsSet);
        return "redirect:/student/" + githubId;

      } else {
        userService.createPossibleSupervisor(identity, email, interestsSet);
        return "redirect:/supervisor/" + githubId;
      }
    } else {
      throw new UserNotFoundException("fail to load user");
    }
  }

  static @NonNull Set<Tag> getInterestsSet(String interests) {
    return Arrays.stream(interests.split(","))
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .map(Tag::new)
        .collect(Collectors.toSet());
  }

  @Secured("ROLE_ADMIN")
  @GetMapping("/admin")
  public String adminInterface(
      @AuthenticationPrincipal OAuth2User user,
      Model model) {
    model.addAttribute("adminName", user.getAttribute("login"));
    return "admin";
  }

  @Secured("ROLE_ADMIN")
  @PostMapping("/admin")
  public String grantRole(Long githubID,
                          RedirectAttributes flashModel,
                          Model model,
                          @AuthenticationPrincipal OAuth2User user) {
    var possibleUser = userService.getUserByID(githubID);
    if (possibleUser != null) {
      userService.createSupervisorProfile(possibleUser.getIdentity(), possibleUser.getEmail(),
          possibleUser.getInterests());
      flashModel.addFlashAttribute("grantedUser", possibleUser.getIdentity().login());
      return "redirect:/success";
    } else {
      model.addAttribute("adminName", user.getAttribute("login"));
      model.addAttribute("errorMessage", "invalid ID");
      return "admin";
    }
  }

  @Secured("ROLE_ADMIN")
  @GetMapping("/success")
  public String success() {
    return "success";
  }

  public static Long extractGithubId(OAuth2User principal) {
    Object idAttr = principal.getAttribute("id");
    if (idAttr == null) {
      throw new IllegalStateException("GitHub ID is missing from provider");
    }
    if (idAttr instanceof Number number) {
      return number.longValue();
    }
    return Long.valueOf(idAttr.toString());
  }


}

