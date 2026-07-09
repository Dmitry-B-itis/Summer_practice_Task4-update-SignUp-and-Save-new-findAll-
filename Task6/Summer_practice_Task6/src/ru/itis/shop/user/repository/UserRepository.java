package ru.itis.shop.user.repository;

import ru.itis.shop.user.domain.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserRepository {


    void save(User user) throws SQLException;

    Optional<User> findByEmail(String email);

    Optional<User> findById(Integer id);

    List<User> findAll();

    List<User> findAllByDescription(String profileDescription);

    void updateDescription(String email, String newProfileDescription);
}
