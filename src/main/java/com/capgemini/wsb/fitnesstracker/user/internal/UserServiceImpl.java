package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }

    public boolean deleteUser(final Long userId) {
        log.info("Attempting to delete User with ID {}", userId);
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            log.info("User with ID {} deleted successfully.", userId);
            return true;
        }
        log.warn("User with ID {} not found for deletion.", userId);
        return false;
    }

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }


    public Optional<User> updateUser(final Long userId, final User User) {
        return userRepository.findById(userId).map(user -> {
            user.setFirstName(User.getFirstName());
            user.setLastName(User.getLastName());
            user.setBirthdate(User.getBirthdate());
            user.setEmail(User.getEmail());
            return userRepository.save(user);
        });
    }

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User> getUserBySimillarEmail(final String email) {
        return userRepository.findUserByEmailOrSimilar(email);
    }


    public List<User> findAllUsersOlderThan(LocalDate date) {
        return userRepository.findAll().stream()
                .filter(user -> user.getBirthdate().isBefore(date))
                .collect(Collectors.toList());
    }

}