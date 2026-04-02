package de.hhu.propra.thesis.applicationlayer.service;

import de.hhu.propra.thesis.applicationlayer.exceptions.WrongInputException;
import de.hhu.propra.thesis.domain.model.shared.Course;
import de.hhu.propra.thesis.domain.model.shared.Tag;
import de.hhu.propra.thesis.domain.model.useragg.GithubIdentity;
import de.hhu.propra.thesis.domain.model.useragg.Role;
import de.hhu.propra.thesis.domain.model.useragg.StudentProfile;
import de.hhu.propra.thesis.domain.model.useragg.SupervisorProfile;
import de.hhu.propra.thesis.domain.model.useragg.User;
import de.hhu.propra.thesis.domain.repository.StudentProfileRepository;
import de.hhu.propra.thesis.domain.repository.SupervisorProfileRepository;
import de.hhu.propra.thesis.domain.repository.UserRepository;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  public UserRepository getUserRepository() {
    return userRepository;
  }

  public StudentProfileRepository getStudentRepository() {
    return studentRepository;
  }

  public SupervisorProfileRepository getSupervisorRepository() {
    return supervisorRepository;
  }

  private final UserRepository userRepository;
  private final StudentProfileRepository studentRepository;
  private final SupervisorProfileRepository supervisorRepository;

  public UserService(UserRepository userRepository, StudentProfileRepository studentRepository,
                     SupervisorProfileRepository supervisorRepository) {
    this.userRepository = userRepository;
    this.studentRepository = studentRepository;
    this.supervisorRepository = supervisorRepository;
  }

  public User getUserByID(Long githubID) {
    return userRepository.findById(githubID).orElse(null);
  }

  public void createSupervisorProfile(GithubIdentity identity, String email,
                                      Set<Tag> interestsSet) {
    checkCreationParameter(identity == null || email.isBlank() || interestsSet.isEmpty());
    SupervisorProfile supervisor = User.createSupervisor(identity, email);
    interestsSet.forEach(supervisor::addInterest);
    supervisorRepository.save(supervisor);
  }

  public void createStudentProfile(GithubIdentity identity, String email, Set<Tag> interestsSet) {
    checkCreationParameter(identity == null || email.isBlank() || interestsSet.isEmpty());
    StudentProfile student = User.createStudent(identity, email);
    interestsSet.forEach(student::addInterest);
    studentRepository.save(student);
  }

  private static void checkCreationParameter(boolean identity) {
    if (identity) {
      throw new WrongInputException("Wrong Creations Parameter");
    }
  }

  public void createPossibleSupervisor(GithubIdentity identity, String email,
                                       Set<Tag> interestsSet) {
    checkCreationParameter(identity == null || email.isBlank() || interestsSet.isEmpty());
    User user = new User(identity, Role.STUDENT, email);
    interestsSet.forEach(user::addInterest);
    userRepository.save(user);
  }

  public StudentProfile getStudentByID(Long githubId) {
    return studentRepository.findById(githubId).orElse(null);
  }

  public void updateStudentInterests(GithubIdentity identity, Set<Tag> interestsSet) {
    checkCreationParameter(identity == null || interestsSet.isEmpty());
    StudentProfile student = getStudentByID(identity.githubUserId());
    interestsSet.forEach(student::addInterest);
    studentRepository.save(student);
  }

  public void updateStudentCourses(GithubIdentity identity, Course course) {
    checkCreationParameter(identity == null || course == null);
    StudentProfile student = getStudentByID(identity.githubUserId());
    student.addPassedCourse(course);
    studentRepository.save(student);
  }

  public SupervisorProfile getSupervisorById(Long githubId) {
    return supervisorRepository.findById(githubId).orElse(null);
  }

  public void updateSupervisorInterests(GithubIdentity identity, Set<Tag> interestsSet) {
    checkCreationParameter(identity == null || interestsSet.isEmpty());
    SupervisorProfile supervisor = getSupervisorById(identity.githubUserId());
    interestsSet.forEach(supervisor::addInterest);
    supervisorRepository.save(supervisor);
  }
}
