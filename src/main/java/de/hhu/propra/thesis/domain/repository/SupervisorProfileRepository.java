package de.hhu.propra.thesis.domain.repository;

import de.hhu.propra.thesis.domain.model.useragg.SupervisorProfile;
import java.util.List;
import java.util.Optional;

public interface SupervisorProfileRepository {
  Optional<SupervisorProfile> findById(Long githubID);

  SupervisorProfile save(SupervisorProfile user);

  List<SupervisorProfile> findAll();
}
