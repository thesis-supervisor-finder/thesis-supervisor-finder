package de.hhu.propra.thesis.infrastructurelayer.reposimplementation;

import de.hhu.propra.thesis.domain.model.useragg.StudentProfile;
import de.hhu.propra.thesis.domain.repository.StudentProfileRepository;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.checkerframework.checker.nullness.qual.NonNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface SqlStudentProfileRepository extends CrudRepository<StudentProfile, Long>, StudentProfileRepository {

  @Override
  default Optional<StudentProfile> findById(@NonNull Long githubID) {
    return findByGithubId(githubID);
  }

  @Query("SELECT * FROM users WHERE github_user_id = :githubID AND role = 'STUDENT'")
  Optional<StudentProfile> findByGithubId(Long githubID);

  @Override
  @Query("SELECT * FROM users WHERE role = 'STUDENT'")
  List<StudentProfile> findAll();
}