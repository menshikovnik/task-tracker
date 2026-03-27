package com.nickmenshikov.tasktracker;

import com.nickmenshikov.tasktracker.dao.UserDao;
import com.nickmenshikov.tasktracker.service.UserService;
import com.nickmenshikov.tasktracker.util.DbUtil;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.SQLException;


@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        String url = servletContext.getInitParameter("jdbc.url");
        String user = servletContext.getInitParameter("jdbc.user");
        String password = servletContext.getInitParameter("jdbc.password");

        DbUtil.init(url, user, password);

        HikariDataSource dataSource;
        try {
        dataSource = DbUtil.getDataSource();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        UserDao userDao = new UserDao(dataSource);
        UserService userService = new UserService(userDao);

        servletContext.setAttribute("dataSource", dataSource);
        servletContext.setAttribute("userService", userService);
    }
}
