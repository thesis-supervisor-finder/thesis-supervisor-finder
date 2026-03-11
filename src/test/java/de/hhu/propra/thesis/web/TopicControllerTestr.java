//package de.hhu.propra.thesis.architecture.web;
//
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import de.hhu.propra.thesis.config.MethodSecurityConfig;
//import de.hhu.propra.thesis.config.SecurityConfig;
//import de.hhu.propra.thesis.controller.TopicController;
//import de.hhu.propra.thesis.applicationlayer.service.TopicService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.web.servlet.MockMvc;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
//  @WebMvcTest(TopicController.class)
//  @Import({MethodSecurityConfig.class, SecurityConfig.class})
//  class TopicControllerTestr {
//
//    @Autowired
//    MockMvc mvc;
//
//    @MockBean
//    TopicService topicService;
//
//
//    @Test
//    void getAllTopicsReturnsTopicsView() throws Exception {
//      Mockito.when(topicService.getAllTopics()).thenReturn(java.util.List.of());
//
//      mvc.perform(get("/topics").with(oauth2Login()))
//          .andExpect(status().isOk())
//          .andExpect(view().name("topics"))
//          .andExpect(model().attributeExists("topics"));
//    }
//
//    @Test
//    void getTopicDetailsOk() throws Exception {
//      var id = java.util.UUID.randomUUID();
//      var topic = Mockito.mock(de.hhu.propra.thesis.domain.model.shared.Topic.class);
//      Mockito.when(topicService.getTopicById(id)).thenReturn(topic);
//
//      mvc.perform(get("/topic/" + id).with(oauth2Login()))
//          .andExpect(status().isOk())
//          .andExpect(view().name("topic"))
//          .andExpect(model().attributeExists("topic"));
//    }
//
//  }
//
//
