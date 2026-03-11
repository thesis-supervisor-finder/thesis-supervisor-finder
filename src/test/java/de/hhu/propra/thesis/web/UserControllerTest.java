//package de.hhu.propra.thesis.web;
//
//import de.hhu.propra.thesis.applicationlayer.service.UserService;
//import de.hhu.propra.thesis.config.MethodSecurityConfig;
//import de.hhu.propra.thesis.config.SecurityConfig;
//import de.hhu.propra.thesis.controller.UserController;
//import de.hhu.propra.thesis.domain.model.useragg.GithubIdentity;
//import de.hhu.propra.thesis.domain.model.useragg.User;
//import de.hhu.propra.thesis.securityhelper.WithMockOAuth2User;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.anySet;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(UserController.class)
//@Import({MethodSecurityConfig.class, SecurityConfig.class})
//class UserControllerTest {
//
//  @Autowired
//  MockMvc mvc;
//
//  @MockBean
//  UserService userService;
//
//  @MockBean
//  ClientRegistrationRepository clientRegistrationRepository;
//
//  @Test
//  void indexReturnsIndexView() throws Exception {
//    mvc.perform(get("/"))
//        .andExpect(view().name("index"));
//  }
//
//  @Test
//  @WithMockOAuth2User(login = "myGithub", roles = "ADMIN")
//  void adminGetAsAdminReturnsAdminViewAndSetsAdminName() throws Exception {
//    mvc.perform(get("/admin"))
//        .andExpect(status().isOk())
//        .andExpect(view().name("admin"))
//        .andExpect(model().attribute("adminName", "myGithub"));
//  }
//
//  @Test
//  @WithMockOAuth2User(roles = "STUDENT")
//  void adminGetAsNonAdminIsForbidden() throws Exception {
//    mvc.perform(get("/admin"))
//        .andExpect(status().isForbidden());
//  }
//
//  @Test
//  @WithMockOAuth2User(login = "myGithub", roles = "ADMIN")
//  void adminPostUserNotFoundReturnsAdminWithError() throws Exception {
//    Mockito.when(userService.getUserByID(99L)).thenReturn(null);
//
//    mvc.perform(post("/admin")
//            .param("githubID", "99")
//            .with(csrf()))
//        .andExpect(status().isOk())
//        .andExpect(view().name("admin"))
//        .andExpect(model().attribute("adminName", "myGithub"))
//        .andExpect(model().attribute("errorMessage", "invalid ID"));
//  }
//
//  @Test
//  @WithMockOAuth2User(login = "myGithub", roles = "ADMIN")
//  void adminPostUserFoundRedirectsToSuccessAndCallsService() throws Exception {
//    User u = Mockito.mock(User.class);
//    GithubIdentity id = Mockito.mock(GithubIdentity.class);
//
//    Mockito.when(u.getIdentity()).thenReturn(id);
//    Mockito.when(u.getEmail()).thenReturn("x@hhu.de");
//    Mockito.when(id.login()).thenReturn("someLogin");
//    Mockito.when(userService.getUserByID(1L)).thenReturn(u);
//
//    mvc.perform(post("/admin")
//            .param("githubID", "1")
//            .with(csrf()))
//        .andExpect(status().is3xxRedirection())
//        .andExpect(redirectedUrl("/success"));
//
//    Mockito.verify(userService).createSupervisorProfile(
//        eq(id),
//        eq("x@hhu.de"),
//        anySet()
//    );
//  }
//
//  @Test
//  @WithMockOAuth2User(roles = "ADMIN")
//  void successAsAdminReturnsSuccessView() throws Exception {
//    mvc.perform(get("/success"))
//        .andExpect(status().isOk())
//        .andExpect(view().name("success"));
//  }
//}