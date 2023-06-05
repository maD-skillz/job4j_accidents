package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.User;
import ru.job4j.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean findUserByName(User user) {
        User newUser = userRepository.findByUsername(user.getUsername());
        return newUser.getUsername() != null;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}
