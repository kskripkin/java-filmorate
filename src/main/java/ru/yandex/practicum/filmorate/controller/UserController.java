package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
public class UserController {

    private final Map<Integer, User> users = new HashMap();
    private int id = 1;

    @GetMapping("/users")
    public Collection<User> getUsers(){
        return users.values();
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user){
        if(user.getEmail() == null ||
                user.getEmail().isBlank() ||
                !user.getEmail().contains("@") ||
                user.getLogin().contains(" ") ||
                user.getLogin().isBlank() ||
                user.getBirthday().isAfter(LocalDate.now())
        ) {
            log.error("Ошибка входящих данных. Проверьте переданные данные.");
            throw new ValidationException("Ошибка входящих данных. Проверьте переданные данные.");
        }
        if(user.getName() == null){
            user.setName(user.getLogin());
        }
        if(user.getId() == 0){
            user.setId(id++);
        }
        users.put(user.getId(), user);
        log.info("Добавлен пользователь: {}", user.toString());
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user){
        if(users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Обновлен пользователь: {}", user.toString());
        } else {
            log.error("Id user not found");
            throw new ValidationException("Id user not found");
        }
        return user;
    }
}
