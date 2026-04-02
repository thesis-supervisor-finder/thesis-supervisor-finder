package de.hhu.propra.thesis.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class O2uthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
  private final DefaultOAuth2UserService defaultOAuth2UserService = new DefaultOAuth2UserService();

  @Value("${thesis.roles.admin}")
  private List<String> admins;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User originalUser = defaultOAuth2UserService.loadUser(userRequest);
    Set<GrantedAuthority> authorities = new HashSet<>(originalUser.getAuthorities());
    if (admins.contains((String) originalUser.getAttribute("login"))) {
      authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
    return new DefaultOAuth2User(authorities, originalUser.getAttributes(), "login");
  }


}


