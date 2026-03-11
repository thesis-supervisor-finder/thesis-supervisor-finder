package de.hhu.propra.thesis.domain.model.util;

public class EmailValidator {
  public static boolean isValidEmail(String email) {
    String emailRegex = "^[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +
        "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    return email.matches(emailRegex);
  }
}
