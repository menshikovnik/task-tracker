package com.nickmenshikov.tasktracker.dao;

import com.nickmenshikov.tasktracker.model.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao extends BaseDao<User>{

    private final DataSource dataSource;
    private final SessionFactory sessionFactory;


    @Override
    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Optional<User> findByUsername(String username) {

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "FROM User WHERE username = :username", User.class)
                    .setParameter("username", username)
                    .uniqueResultOptional();
        }
    }
}
