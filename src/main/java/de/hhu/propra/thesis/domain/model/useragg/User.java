package de.hhu.propra.thesis.domain.model.useragg;

import de.hhu.propra.thesis.domain.model.shared.Tag;
import de.hhu.propra.thesis.domain.model.util.EmailValidator;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public class User {
  @Id
  @Embedded(onEmpty = Embedded.OnEmpty.USE_NULL)
  @Column("github_user_id")
  private GithubIdentity identity;
  private final Role role;
  private final String email;

  @MappedCollection(idColumn = "user_id")
  private Set<Tag> interests = new HashSet<>();

  @SuppressFBWarnings(
      value = "CT_CONSTRUCTOR_THROW",
      justification = "Domain constructor validates invariants; class has no finalizer and " +
          "is not exposed during construction"
  )
  public User(GithubIdentity identity, Role role, String email) {
    GithubIdentity validIdentity = Objects.requireNonNull(identity, "identity must not be null");
    Role validRole = Objects.requireNonNull(role, "role must not be null");
    String validEmail = validateEmail(email, "Invalid Email");

    this.identity = validIdentity;
    this.role = validRole;
    this.email = validEmail;
  }

  public GithubIdentity getIdentity() {
    return identity;
  }

  public Role getRole() {
    return role;
  }

  public String getEmail() {
    return email;
  }

  public Set<Tag> getInterests() {
    return Collections.unmodifiableSet(interests);
  }

  public void addInterest(Tag interest) {
    interests.add(Objects.requireNonNull(interest, "interest must not be null"));
  }

  public void replaceInterests(Set<Tag> newInterests) {
    Objects.requireNonNull(newInterests, "interests must not be null");
    interests.clear();
    for (Tag t : newInterests) {
      interests.add(Objects.requireNonNull(t, "interest must not be null"));
    }
  }

  public static StudentProfile createStudent(GithubIdentity identity, String email) {
    return new StudentProfile(identity, email);
  }

  public static SupervisorProfile createSupervisor(GithubIdentity identity, String email) {
    return new SupervisorProfile(identity, email);
  }

  private static String validateEmail(String value, String message) {
    if (value == null || value.isBlank() || !EmailValidator.isValidEmail(value)) {
      throw new IllegalArgumentException(message);
    }
    return value.trim();
  }
}
