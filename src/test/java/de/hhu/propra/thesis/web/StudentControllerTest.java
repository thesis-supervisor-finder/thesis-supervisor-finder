package de.hhu.propra.thesis.web;

import de.hhu.propra.thesis.applicationlayer.service.MatchingService;
import de.hhu.propra.thesis.applicationlayer.service.UserService;
import de.hhu.propra.thesis.config.MethodSecurityConfig;
import de.hhu.propra.thesis.config.SecurityConfig;
import de.hhu.propra.thesis.controller.StudentController;
import de.hhu.propra.thesis.domain.model.useragg.StudentProfile;
import de.hhu.propra.thesis.domain.model.useragg.GithubIdentity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
@Import({MethodSecurityConfig.class, SecurityConfig.class})
class StudentControllerTest {

  @Autowired
  MockMvc mvc;

  @MockBean
  UserService studentService;

  @MockBean
  MatchingService matchingService;

  private StudentProfile mockStudent(long githubId, String login) {
    StudentProfile student = Mockito.mock(StudentProfile.class);
    GithubIdentity identity = Mockito.mock(GithubIdentity.class);

    Mockito.when(student.getIdentity()).thenReturn(identity);
    Mockito.when(identity.githubUserId()).thenReturn(githubId);
    Mockito.when(identity.login()).thenReturn(login);

    return student;
  }

//  @Test
//  void studentGetSelfOk() throws Exception {
//    StudentProfile s = mockStudent(123L, "studentLogin");
//    Mockito.when(studentService.getStudentByID(123L)).thenReturn(s);
//
//    mvc.perform(get("/student/123")
//            .with(oauth2Login().attributes(a -> a.put("id", 123))))
//        .andExpect(status().isOk())
//        .andExpect(view().name("student"))
//        .andExpect(model().attributeExists("student"));
//  }

//  @Test
//  void studentGetNotSelfForbidden() throws Exception {
//    mvc.perform(get("/student/123")
//            .with(oauth2Login().attributes(a -> a.put("id", 999))))
//        .andExpect(status().isForbidden());
//  }
//
//  @Test
//  void saveStudentTagsBlankReturnsFormWithError() throws Exception {
//    StudentProfile s = mockStudent(123L, "studentLogin");
//    Mockito.when(studentService.getStudentByID(123L)).thenReturn(s);
//
//    mvc.perform(post("/student/123/tags")
//            .with(csrf())
//            .with(oauth2Login().attributes(a -> a.put("id", 123)))
//            .param("newInterests", "   "))
//        .andExpect(status().isOk())
//        .andExpect(view().name("student-tags"))
//        .andExpect(model().attributeExists("interestsError"))
//        .andExpect(model().attributeExists("student"));
//  }
//
//  @Test
//  void saveStudentCourseBlankReturnsFormWithError() throws Exception {
//    StudentProfile s = mockStudent(123L, "studentLogin");
//    Mockito.when(studentService.getStudentByID(123L)).thenReturn(s);
//
//    mvc.perform(post("/student/123/courses")
//            .with(csrf())
//            .with(oauth2Login().attributes(a -> a.put("id", 123)))
//            .param("passedCourse", ""))
//        .andExpect(status().isOk())
//        .andExpect(view().name("student-courses"))
//        .andExpect(model().attributeExists("coursesError"))
//        .andExpect(model().attributeExists("student")); // important si la vue utilise student.identity.login()
//  }
//
//  @Test
//  void matchingGetAddsBestTopics() throws Exception {
//    StudentProfile s = mockStudent(123L, "studentLogin");
//    Mockito.when(studentService.getStudentByID(123L)).thenReturn(s);
//    Mockito.when(matchingService.getBestTopics(s)).thenReturn(java.util.List.of());
//
//    mvc.perform(get("/student/123/matching")
//            .with(oauth2Login().attributes(a -> a.put("id", 123))))
//        .andExpect(status().isOk())
//        .andExpect(view().name("student-matching"))
//        .andExpect(model().attributeExists("bestTopics"))
//        .andExpect(model().attributeExists("student"));
//  }
}
