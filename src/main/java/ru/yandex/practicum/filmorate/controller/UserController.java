package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.UserStorage;

import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserStorage userStorage;
    private final UserService userService;

    @GetMapping("/users")
    public Collection<User> getUsers(){
        log.info("GET /users");
        return userStorage.getUsers();
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user){
        log.info("POST /users");
        return userStorage.createUser(user);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user){
        log.info("PUT /users");
        return userStorage.updateUser(user);
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id){
        log.info("GET /users/{id}");
        return userService.getUser(id);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public User addFriend(@PathVariable int id, @PathVariable int friendId){
        log.info("PUT /users/{id}/friends/{friendId}");
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable int id, @PathVariable int friendId){
        log.info("DELETE /users/{id}/friends/{friendId}");
        return userService.deleteFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public Collection<User> getFriends(@PathVariable int id){
        log.info("GET /users/{id}/friends");
        return userService.showListFriends(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Collection<User> getJoinListFriends(@PathVariable int id, @PathVariable int otherId){
        log.info("GET /users/{id}/friends/common/{otherId}");
        return userService.showJoinListFriends(id, otherId);
    }
}
