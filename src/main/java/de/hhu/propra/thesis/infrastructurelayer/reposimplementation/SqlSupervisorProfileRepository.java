package de.hhu.propra.thesis.infrastructurelayer.reposimplementation;

import de.hhu.propra.thesis.domain.model.useragg.SupervisorProfile;
import de.hhu.propra.thesis.domain.repository.SupervisorProfileRepository;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.checkerframework.checker.nullness.qual.NonNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface SqlSupervisorProfileRepository
    extends CrudRepository<SupervisorProfile, Long>, SupervisorProfileRepository {

  @Override
  default Optional<SupervisorProfile> findById(@NonNull Long githubID) {
    return findByGithubId(githubID);
  }

  @Query("SELECT * FROM users WHERE github_user_id = :githubID AND role = 'SUPERVISOR'")
  Optional<SupervisorProfile> findByGithubId(Long githubID);

  @Override
  @Query("SELECT * FROM users WHERE role = 'SUPERVISOR'")
  List<SupervisorProfile> findAll();
}