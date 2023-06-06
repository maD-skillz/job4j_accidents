package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.User;
import ru.job4j.repository.AuthorityRepository;
import ru.job4j.service.UserService;

@Controller
@AllArgsConstructor
public class RegControl {

    private final PasswordEncoder encoder;
    private final UserService users;
    private final AuthorityRepository authorities;

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        users.save(user);
        return "redirect:/login";
    }

    @GetMapping("/reg")
    public String regPage() {
        return "reg";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleUniqueConstraintViolation(
            DataIntegrityViolationException ex) {
        String message =
                "The requested operation could not be completed"
                + " due to a unique constraint violation.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
