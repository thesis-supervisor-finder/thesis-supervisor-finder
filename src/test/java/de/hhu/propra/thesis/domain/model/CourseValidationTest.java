package de.hhu.propra.thesis.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import de.hhu.propra.thesis.domain.model.shared.Course;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CourseValidationTest {

  @Test
  @DisplayName("Course rejects null name")
  void rejectsNullName() {
    assertThatThrownBy(() -> new Course(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("Course rejects blank name")
  void rejectsBlankName() {
    assertThatThrownBy(() -> new Course(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("must not be blank");
  }

  @Test
  @DisplayName("Course rejects whitespace-only name")
  void rejectsWhitespaceOnlyName() {
    assertThatThrownBy(() -> new Course("   "))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("must not be blank");
  }

  @Test
  @DisplayName("Course trims name")
  void trimsName() {
    Course c = new Course("  Compilerbau  ");
    assertThat(c.name()).isEqualTo("Compilerbau");
  }

}
