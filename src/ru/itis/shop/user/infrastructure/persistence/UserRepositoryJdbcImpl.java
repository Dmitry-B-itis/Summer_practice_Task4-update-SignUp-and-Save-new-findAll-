package ru.itis.shop.user.infrastructure.persistence;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class UserRepositoryJdbcImpl implements UserRepository {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/usersData";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "12345";

    private final String fileName;

    private final UserMapper userMapper;

    public UserRepositoryJdbcImpl(String fileName, UserMapper userMapper) {
        this.fileName = fileName;
        this.userMapper = userMapper;
    }

    @Override
    public void save(User user) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            try (Statement statement = connection.createStatement()) {
                String id = UUID.randomUUID().toString();
                user.setId(id);
                statement.executeUpdate("INSERT INTO accountData(id, email, password, profileDescription) values ('" + user.getId() + "', '" + user.getEmail() + "', '" + user.getPassword() + "', '" + user.getProfileDescription() + "')");

                } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){

            String line = reader.readLine();

            while (line != null) {

                User user = userMapper.fromLine(line);

                if (user.getEmail().equals(email)) {
                    return Optional.of(user);
                }

                line = reader.readLine();
            }

            return Optional.empty();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findById(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){

            String line = reader.readLine();

            while (line != null) {

                User user = userMapper.fromLine(line);

                if (user.getId().equals(id)) {
                    return Optional.of(user);
                }

                line = reader.readLine();
            }

            return Optional.empty();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> readAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            List<User> users = new ArrayList<>();

            String line = reader.readLine();

            while (line != null ) {
                User user = userMapper.fromLine(line);
                users.add(user);
                line = reader.readLine();
            }

            return users;

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void saveAfterRaed(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for(User us : users) {
                writer.write(userMapper.toLine(us));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("select * from accountData")) {
                    while (resultSet.next()) {
                        User user = new User(resultSet.getString("id"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getString("profileDescription"));
                        users.add(user);
                    }
                }
            }

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return users;
    }
}
