package ru.itis.shop.user.application;

import ru.itis.shop.user.domain.User;
import ru.itis.shop.user.repository.UserRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(String email, String password, String profileDescription) throws SQLException {
        User user = new User(email, password, profileDescription);
        userRepository.save(user);
    }

    public boolean signIn(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get().getPassword().trim().equals(password.trim());
        } else return false;
    }

    public boolean findById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            System.out.println(userOptional.get().getId() + " | " +
                    userOptional.get().getEmail() + " | " +
                    userOptional.get().getPassword() + " | " +
                    userOptional.get().getProfileDescription());
        } else {
            System.out.println("пользователь не найден");
        }
        return false;
    }

    public String findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get().getEmail();
        } else {
            return null;
        }
    }

    public void displayInfo() {
        userRepository.findAll();

        List<User> users = userRepository.findAll();
        for(User us : users) {
            System.out.println(us.getId() + "|" +
                    us.getEmail() + "|" +
                    us.getPassword() + "|" +
                    us.getProfileDescription());
        }
    }

    public void displayByDescription(String profileDescription) {
        userRepository.findAllByDescription(profileDescription);

        List<User> findAllByDescriptionf = userRepository.findAllByDescription(profileDescription);
        for(User us : findAllByDescriptionf) {
            System.out.println(us.getId() + "|" +
                    us.getEmail() + "|" +
                    us.getPassword() + "|" +
                    us.getProfileDescription());
        }

    }


    public void updateDescription(String requiredEmail, String newProfileDescription) {
        userRepository.updateDescription(requiredEmail, newProfileDescription);
    }
}
