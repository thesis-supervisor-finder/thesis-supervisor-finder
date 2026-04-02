package de.hhu.propra.thesis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


  @Bean
  public SecurityFilterChain configure(HttpSecurity chainBuilder) throws Exception {
    chainBuilder.authorizeHttpRequests(
        c -> c.requestMatchers("/", "/error", "/css/*", "/img/**").permitAll()
            .anyRequest().authenticated()
    ).oauth2Login(oauth -> oauth
        .loginPage("/")
        .defaultSuccessUrl("/user", true)
    );

    return chainBuilder.build();
  }
}
