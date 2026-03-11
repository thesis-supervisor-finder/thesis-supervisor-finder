package de.hhu.propra.thesis.domain.repository;

import de.hhu.propra.thesis.domain.model.useragg.User;
import java.util.Optional;

public interface UserRepository {
  Optional<User> findById(Long githubID);
  User save(User user);
}
