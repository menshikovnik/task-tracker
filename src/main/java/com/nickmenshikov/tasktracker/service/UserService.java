package com.nickmenshikov.tasktracker.service;

import com.nickmenshikov.tasktracker.dao.UserDao;
import com.nickmenshikov.tasktracker.exception.BadRequestException;
import com.nickmenshikov.tasktracker.exception.UserNotFoundException;
import com.nickmenshikov.tasktracker.exception.UsernameAlreadyTakenException;
import com.nickmenshikov.tasktracker.exception.InvalidPasswordException;
import com.nickmenshikov.tasktracker.model.User;
import com.nickmenshikov.tasktracker.util.PasswordUtil;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User register(String username, String password, String confirmPassword) {

        userDao.findByUsername(username).ifPresent(
                user -> {
                    throw new UsernameAlreadyTakenException("Username is already taken: " + username);
                }
        );

        if (!password.equals(confirmPassword)) {
            throw new BadRequestException("Passwords don't match");
        }

        User newUser = new User();

        newUser.setUsername(username);
        newUser.setPasswordHash(PasswordUtil.hashPassword(password));
        newUser.setCreatedAt(Instant.now());

        return userDao.save(newUser);
    }

    public User login(String username, String password) {

        User user = userDao.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User not found " + username)
        );

        if (!PasswordUtil.checkPassword(password, user.getPasswordHash())) {
            throw new InvalidPasswordException("Invalid password");
        }

        return user;
    }
}
