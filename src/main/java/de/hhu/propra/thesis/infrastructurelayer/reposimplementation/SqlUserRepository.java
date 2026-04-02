package de.hhu.propra.thesis.infrastructurelayer.reposimplementation;

import de.hhu.propra.thesis.domain.model.useragg.User;
import de.hhu.propra.thesis.domain.repository.UserRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqlUserRepository extends CrudRepository<User, Long>, UserRepository {

}