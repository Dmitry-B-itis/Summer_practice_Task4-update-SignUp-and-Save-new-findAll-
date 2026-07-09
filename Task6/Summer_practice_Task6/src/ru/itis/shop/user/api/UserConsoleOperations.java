package ru.itis.shop.user.api;

import ru.itis.shop.user.application.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class UserConsoleOperations {

    private final UserService userService;
    private final Scanner scanner;

    public UserConsoleOperations(UserService userService) {
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() throws SQLException {
        printUserMenu();

        String command = scanner.nextLine();

        switch (command) {
            case "1": {
                signUp();
            }
            break;
            case "2": {
                signIn();
            }
            break;
            case "3": {
                findById();
            }
            break;
            case "4": {
                updateDescription();
            }
            break;
            case "5": {
                displayInfo();
            }
            break;
            case "6": {
                displayByDescription();
            }
            break;
            case "0": {
                System.exit(0);
            }
        }
    }

    private static void printUserMenu() {
        System.out.println("1. Регистрация пользователя");
        System.out.println("2. Вход в систему");
        System.out.println("3. Найти пользователя по id");
        System.out.println("4. обновить описание по почте");
        System.out.println("5. показать подопытных");
        System.out.println("6. показать подопотных по описанию профиля");
        System.out.println("0. Выход");
    }

    private void signUp() throws SQLException {
        System.out.println("Сейчас будем регистрировать пользователя");
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();
        System.out.println("Введите описание профиля:");
        String profileDescription = scanner.nextLine();

        userService.signUp(email, password, profileDescription);
    }


    private void signIn() {
        System.out.println("Вы можете войти в приложение");
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите password:");
        String password = scanner.nextLine();

        if (userService.signIn(email, password)) {
            System.out.println("Вы вошли в приложение");
        } else {
            System.out.println("Email или пароль не верны");
        }
    }

    private void findById() {
        System.out.println("введите айди");
        Integer id = Integer.valueOf(scanner.nextLine());
        userService.findById(id);
    }

    private void updateDescription() {
        System.out.println("введите email");
        String requiredEmail = scanner.nextLine();
        System.out.println("введите новое описание");
        String newProfileDescription = scanner.nextLine();
        userService.updateDescription(requiredEmail, newProfileDescription);
    }

    private void displayInfo() {
        System.out.println();
        userService.displayInfo();
        System.out.println();
    }

    private void displayByDescription() {
        System.out.println("введи описание профиля: ");
        String profileDescription = scanner.nextLine();
        userService.displayByDescription(profileDescription);
    }
}
