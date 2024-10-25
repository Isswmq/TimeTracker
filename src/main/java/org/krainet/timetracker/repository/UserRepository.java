package org.krainet.timetracker.repository;

import org.krainet.timetracker.model.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findAll();

    List<User> findAll(Pageable pageable);

    Optional<User> findById(Long id);
}
