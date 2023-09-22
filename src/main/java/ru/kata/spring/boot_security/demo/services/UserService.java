package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getUsers();

    User showUser(Long id);

    void deleteUser(Long id);

    void saveUser(User user);

    List<String> getAllUsername();

}
