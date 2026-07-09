package ru.itis.shop.app;

import ru.itis.shop.infrastructure.jdbc.DriverManagerDataSource;
import ru.itis.shop.user.api.UserConsoleOperations;
import ru.itis.shop.user.application.UserService;
import ru.itis.shop.user.infrastructure.persistence.UserRepositoryJdbcImpl;


import javax.sql.DataSource;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        DataSource dataSource  = new DriverManagerDataSource("jdbc:postgresql://localhost:5432/usersData",
                "postgres", "12345");

        UserRepositoryJdbcImpl userFileRepository = new UserRepositoryJdbcImpl(dataSource);
        UserService userService = new UserService(userFileRepository);

        UserConsoleOperations operations = new UserConsoleOperations(userService);

        while (true) {
            operations.showMenu();
        }
    }
}
