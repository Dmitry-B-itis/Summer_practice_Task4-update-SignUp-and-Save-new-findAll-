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
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO accountData(email, password, profileDescription) values (?, ?, ?)")) {

                preparedStatement.setString(1, user.getEmail());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getProfileDescription());

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {

        List<User> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("select * from accountData")) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
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

        for (User us : users) {
            if (us.getEmail().trim().equals(email.trim())) {
                return Optional.of(us);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(Integer id) {

        List<User> users = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("select * from accountData")) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
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

        for (User us : users) {
            if (us.getId().equals(id)) {
                return Optional.of(us);
            }
        }
        return Optional.empty();
    }

    @Override
    public void updateDescription(String requiredEmail, String newProfileDescription) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE accountdata set profiledescription = ? where email = ?")) {
                preparedStatement.setString(1, newProfileDescription);
                preparedStatement.setString(2, requiredEmail);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
