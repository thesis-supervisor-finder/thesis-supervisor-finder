package de.hhu.propra.thesis.domain.model.useragg;

import de.hhu.propra.thesis.domain.model.shared.Course;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public final class StudentProfile extends User {

  @MappedCollection(idColumn = "user_id")
  private Set<Course> passedCourses = new HashSet<>();

  public StudentProfile(GithubIdentity identity, String email) {
    super(identity, Role.STUDENT, email);
  }

  public Set<Course> getPassedCourses() {
    return Collections.unmodifiableSet(passedCourses);
  }

  public void addPassedCourse(Course course) {
    passedCourses.add(Objects.requireNonNull(course, "course must not be null"));
  }

  public void replacePassedCourses(Set<Course> courses) {
    Objects.requireNonNull(courses, "passedCourses must not be null");
    passedCourses.clear();
    for (Course c : courses) {
      passedCourses.add(Objects.requireNonNull(c, "course must not be null"));
    }
  }
}

