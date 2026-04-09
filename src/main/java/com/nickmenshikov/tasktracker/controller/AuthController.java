package com.nickmenshikov.tasktracker.controller;

import com.nickmenshikov.tasktracker.dto.LoginRequest;
import com.nickmenshikov.tasktracker.dto.RegistrationRequest;
import com.nickmenshikov.tasktracker.model.User;
import com.nickmenshikov.tasktracker.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "/login", version = "1.0")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest request, HttpSession httpSession) {
        User user = userService.login(request.username(), request.password());
        httpSession.setAttribute("user", user);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/register", version = "1.0")
    public ResponseEntity<Void> register(@Valid @RequestBody RegistrationRequest request) {
        User user = userService.register(request.username(), request.password(), request.confirmPassword());
        return ResponseEntity.created(URI.create("/api/users/" + user.getId())).build();
    }

    @PostMapping(value = "/logout", version = "1.0")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }
}
