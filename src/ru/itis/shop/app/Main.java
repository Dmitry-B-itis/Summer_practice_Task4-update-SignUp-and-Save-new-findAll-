package ru.itis.shop.app;

import ru.itis.shop.user.api.UserConsoleOperations;
import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.infrastructure.persistence.UserRepositoryJdbcImpl;
import ru.itis.shop.user.infrastructure.persistence.UserMapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {



    public static void main(String[] args) throws SQLException {

        UserRepositoryJdbcImpl userFileRepository = new UserRepositoryJdbcImpl("users.txt", new UserMapper());
        UserService userService = new UserService(userFileRepository);

        UserConsoleOperations operations = new UserConsoleOperations(userService);

        while (true) {
            operations.showMenu();
        }
    }
}
