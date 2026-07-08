package ru.itis.shop.user.repository;

import ru.itis.shop.user.domain.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserRepository {


    void save(User user) throws SQLException;

    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);

    List<User> readAll();

    void saveAfterRaed(List<User> users);

    List<User> findAll();

    List<User> findAllByDescription(String profileDescription);
}
