package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.UserStorage;

import java.util.*;

@Slf4j
@RestController
public class UserController {

    private UserStorage userStorage = new InMemoryUserStorage();
    private UserService userService = new UserService();

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

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id){
        return userService.getUser(id);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId){
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public Collection<User> getFriends(@PathVariable int id){
        return userService.showListFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Collection<User> getJoinListFriends(@PathVariable int id, @PathVariable int otherId){
        return userService.showJoinListFriends(id, otherId);
    }
}
