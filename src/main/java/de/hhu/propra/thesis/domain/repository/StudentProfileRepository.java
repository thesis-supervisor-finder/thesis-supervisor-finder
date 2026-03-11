package de.hhu.propra.thesis.domain.repository;

import de.hhu.propra.thesis.domain.model.useragg.StudentProfile;
import java.util.List;
import java.util.Optional;

public interface StudentProfileRepository {
  Optional<StudentProfile> findById(Long githubID);

  StudentProfile save(StudentProfile user);

  List<StudentProfile> findAll();
}
