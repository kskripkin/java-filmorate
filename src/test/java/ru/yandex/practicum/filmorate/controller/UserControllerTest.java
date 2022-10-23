package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private UserController userController;
    private User user;
    private UserStorage userStorage;
    private UserService userService;

    @BeforeEach
    public void BeforeEach(){
        userStorage = new InMemoryUserStorage();
        userService = new UserService(userStorage);
        userController = new UserController(userStorage, userService);
        //user = new User();
        user.setName("Вася");
        user.setId(1);
        user.setLogin("Vasja123");
        user.setEmail("Vasja@yandex.ru");
        user.setBirthday(LocalDate.of(2000, 10, 11));
    }

    @Test
    void createUserEmailIsBlanc() {
        user.setEmail("");
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            userController.createUser(user);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void createUserEmailNonIncludeDog() {
        user.setEmail("Vasjayandex.ru");
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            userController.createUser(user);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void createUserLoginNonSpace() {
        user.setLogin("Vasja 123");
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            userController.createUser(user);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void createUserLoginIsBlanc() {
        user.setEmail("");
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            userController.createUser(user);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void createUserBirthdayAfterNow() {
        user.setBirthday(LocalDate.now().plusDays(1));
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            userController.createUser(user);
        });
        assertNotNull(thrown.getMessage());
    }
}