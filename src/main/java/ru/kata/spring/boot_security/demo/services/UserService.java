package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    List<User> getUsers();

    void deleteUser(Long id);

    void saveUser(User user);

    Optional<User> showUser(Long id);

    User findByName(String username);

}
