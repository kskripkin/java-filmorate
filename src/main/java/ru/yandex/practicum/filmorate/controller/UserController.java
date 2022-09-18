package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.UserStorage;

import java.util.*;

@Slf4j
@RestController
public class UserController {

    private UserStorage userStorage = new InMemoryUserStorage();

    @GetMapping("/users")
    public Collection<User> getUsers(){
        return userStorage.getUsers();
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user){
        return userStorage.createUser(user);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user){
        return userStorage.updateUser(user);
    }
}
