package de.hhu.propra.thesis.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.hhu.propra.thesis.domain.model.shared.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TagValidationTest {

  @Test
  @DisplayName("Tag rejects null")
  void rejectsNull() {
    assertThatThrownBy(() -> new Tag(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("Tag rejects blank")
  void rejectsBlank() {
    assertThatThrownBy(() -> new Tag(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("must not be blank");
  }

  @Test
  @DisplayName("Tag rejects whitespace-only")
  void rejectsWhitespaceOnly() {
    assertThatThrownBy(() -> new Tag("   "))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("must not be blank");
  }

  @Test
  @DisplayName("Tag trims name")
  void trimsName() {
    Tag t = new Tag("  Rust  ");
    assertThat(t.name()).isEqualTo("Rust"); // adjust accessor if different
  }

}
