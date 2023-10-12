package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }


    @Transactional
    @Override
    public void deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public void saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (user != null) {
            if (user.getPassword().isEmpty() || user.getPassword() == null) {
                user.setPassword(userFromDB.getPassword());
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userRepository.save(user);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> showUser(Long id) {
        Optional<User> userInDb = userRepository.findById(id);
        return userInDb;
    }

    public User findByName(String username) {
        return userRepository.findByUsername(username);
    }
}
