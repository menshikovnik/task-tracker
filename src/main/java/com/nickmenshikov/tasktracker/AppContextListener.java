package com.nickmenshikov.tasktracker;

import com.nickmenshikov.tasktracker.dao.TaskDao;
import com.nickmenshikov.tasktracker.dao.UserDao;
import com.nickmenshikov.tasktracker.service.TaskService;
import com.nickmenshikov.tasktracker.service.UserService;
import com.nickmenshikov.tasktracker.util.DataSourceFactory;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import javax.sql.DataSource;


@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        Dotenv dotenv = Dotenv.configure().load();

        String url = dotenv.get("JDBC_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        DataSource dataSource = DataSourceFactory.create(url, user, password);

        UserDao userDao = new UserDao(dataSource);
        UserService userService = new UserService(userDao);
        TaskDao taskDao = new TaskDao(dataSource);
        TaskService taskService = new TaskService(taskDao);

        servletContext.setAttribute("dataSource", dataSource);
        servletContext.setAttribute("userService", userService);
        servletContext.setAttribute("taskService", taskService);
    }
}
