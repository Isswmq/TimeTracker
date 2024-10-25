package org.krainet.timetracker.service;

import org.krainet.timetracker.dto.user.UserCreateEditDto;
import org.krainet.timetracker.model.user.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findByEmail(String email);

    User create(UserCreateEditDto userDto);

    List<User> findAll(Pageable pageable);

    Optional<User> findById(Long id);
}
