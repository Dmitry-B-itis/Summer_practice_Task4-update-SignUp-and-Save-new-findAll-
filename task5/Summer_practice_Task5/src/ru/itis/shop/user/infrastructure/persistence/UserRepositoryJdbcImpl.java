package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import javax.sql.DataSource;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class UserRepositoryJdbcImpl implements UserRepository {

    private final DataSource dataSource;

    public UserRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(User user) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("INSERT INTO accountData(email, password, " +
                        "profileDescription) values ('" + user.getEmail() + "', '" + user.getPassword() + "'," +
                        " '" + user.getProfileDescription() + "')");

                } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

        @Override
        public Optional<User> findByEmail(String email) {
            return Optional.empty();
        }

        @Override
        public Optional<User> findById(String id) {
            return Optional.empty();
        }

        @Override
        public List<User> readAll() {
            return null;
        }

        @Override
        public void saveAfterRaed(List<User> users) {

        }

    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("select * from accountData")) {
                    while (resultSet.next()) {
                        User user = new User(resultSet.getInt("id"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getString("profileDescription"));
                        users.add(user);
                    }
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return users;
    }

    @Override
    public List<User> findAllByDescription(String profileDescription) {

        List<User> users = new ArrayList<>();

        List<User> usersFindByProfileDescription = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("select * from accountData")) {
                    while (resultSet.next()) {
                        User user = new User(resultSet.getInt("id"),
                                resultSet.getString("email"),
                                resultSet.getString("password"),
                                resultSet.getString("profileDescription"));
                        users.add(user);
                    }
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        for(User us : users) {
            if (us.getProfileDescription().trim().equals(profileDescription.trim())){
                usersFindByProfileDescription.add(us);
            }
        }

        return usersFindByProfileDescription;
    }


}
