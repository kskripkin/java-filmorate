package ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validate.Validate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage{

    private final static Map<Integer, User> users = new HashMap();
    private int id = 1;

    public Map<Integer, User> getUserSourceMap(){
        return users;
    }

    public Collection<User> getUsers(){
        return users.values();
    }

    public User createUser(User user){
        if(Validate.isValidUser(user)) {
            if(user.getName() == null || user.getName() == ""){
                user.setName(user.getLogin());
            }
            if(user.getId() == 0){
                user.setId(id++);
            }
            users.put(user.getId(), user);
            log.info("Добавлен пользователь: {}", user.toString());
            return user;
        } else {
            log.error("Ошибка входящих данных. Проверьте переданные данные.");
            throw new ValidationException("Ошибка входящих данных. Проверьте переданные данные.");
        }
    }

    public User updateUser(User user){
        if(users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.info("Обновлен пользователь: {}", user.toString());
        } else {
            log.error("Id user not found");
            throw new UserNotFoundException("Id user not found");
        }
        return user;
    }

}
