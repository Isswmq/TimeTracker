package org.krainet.timetracker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.krainet.timetracker.dto.user.UserCreateEditDto;
import org.krainet.timetracker.model.user.Role;
import org.krainet.timetracker.model.user.User;
import org.krainet.timetracker.repository.UserRepository;
import org.krainet.timetracker.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        log.debug("Attempting to find user by email: {}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public User create(UserCreateEditDto userDto) {
        log.debug("Attempting to create user with dto: {}", userDto);
        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getRawPassword())
                .role(Role.USER)
                .build();
        log.debug("Created user: {}", user);
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll(Pageable pageable) {
        log.debug("Attempting to find all users with pageable: {}", pageable);
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        log.debug("Attempting to find user by id: {}", id);
        return userRepository.findById(id);
    }
}
