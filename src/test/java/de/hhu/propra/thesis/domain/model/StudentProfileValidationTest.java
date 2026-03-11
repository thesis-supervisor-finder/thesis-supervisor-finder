package de.hhu.propra.thesis.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.hhu.propra.thesis.domain.model.shared.Course;
import de.hhu.propra.thesis.domain.model.shared.Tag;
import de.hhu.propra.thesis.domain.model.useragg.GithubIdentity;
import de.hhu.propra.thesis.domain.model.useragg.StudentProfile;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudentProfileValidationTest {

  private static GithubIdentity identity(long id) {
    return new GithubIdentity(id, "some-login");
  }

  @Test
  @DisplayName("new StudentProfile starts with empty passedCourses and interests")
  void startsEmpty() {
    long githubUserId = 123L;

    StudentProfile profile = new StudentProfile(identity(githubUserId), "a@b.de");

    assertThat(profile.getIdentity().githubUserId()).isEqualTo(githubUserId);
    assertThat(profile.getPassedCourses()).isEmpty();
    assertThat(profile.getInterests()).isEmpty();
  }

  @Test
  @DisplayName("passedCourses is unmodifiable")
  void passedCoursesUnmodifiable() {
    StudentProfile profile = new StudentProfile(identity(123L), "a@b.de");
    profile.addPassedCourse(new Course("Compilerbau"));

    assertThatThrownBy(() -> profile.getPassedCourses().add(new Course("Datenbanken 2")))
        .isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  @DisplayName("interests is unmodifiable")
  void interestsUnmodifiable() {
    StudentProfile profile = new StudentProfile(identity(123L), "a@b.de");
    profile.addInterest(new Tag("Rust"));

    assertThatThrownBy(() -> profile.getInterests().add(new Tag("Formale Methoden")))
        .isInstanceOf(UnsupportedOperationException.class);
  }

  @Test
  @DisplayName("replacePassedCourses copies input (mutating input does not affect profile)")
  void replacePassedCoursesCopiesInput() {
    StudentProfile profile = new StudentProfile(identity(123L), "a@b.de");

    Set<Course> courses = new HashSet<>();
    courses.add(new Course("Compilerbau"));

    profile.replacePassedCourses(courses);

    courses.add(new Course("Datenbanken 2"));

    assertThat(profile.getPassedCourses())
        .extracting(Course::name)
        .containsExactlyInAnyOrder("Compilerbau");
  }

  @Test
  @DisplayName("replaceInterests copies input (mutating input does not affect profile)")
  void replaceInterestsCopiesInput() {
    StudentProfile profile = new StudentProfile(identity(123L), "a@b.de");

    Set<Tag> tags = new HashSet<>();
    tags.add(new Tag("Rust"));

    profile.replaceInterests(tags);

    tags.add(new Tag("Formale Methoden"));

    assertThat(profile.getInterests())
        .extracting(Tag::name)
        .containsExactlyInAnyOrder("Rust");
  }

  @Test
  @DisplayName("addPassedCourse rejects null")
  void addPassedCourseRejectsNull() {
    StudentProfile profile = new StudentProfile(identity(123L), "a@b.de");

    assertThatThrownBy(() -> profile.addPassedCourse(null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  @DisplayName("addInterest rejects null")
  void addInterestRejectsNull() {
    StudentProfile profile = new StudentProfile(identity(123L), "a@b.de");

    assertThatThrownBy(() -> profile.addInterest(null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  @DisplayName("replacePassedCourses rejects null set")
  void replacePassedCoursesRejectsNullSet() {
    StudentProfile profile = new StudentProfile(identity(123L), "a@b.de");

    assertThatThrownBy(() -> profile.replacePassedCourses(null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  @DisplayName("replaceInterests rejects null set")
  void replaceInterestsRejectsNullSet() {
    StudentProfile profile = new StudentProfile(identity(123L), "a@b.de");

    assertThatThrownBy(() -> profile.replaceInterests(null))
        .isInstanceOf(NullPointerException.class);
  }
}
