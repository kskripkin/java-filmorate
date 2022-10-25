package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.UserStorage;
import ru.yandex.practicum.filmorate.validate.Validate;

import java.util.Collection;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;

    public UserService(UserStorage userStorage){
        this.userStorage = userStorage;
    }

    public User addFriend(Integer idFirst, Integer idSecond){
        if(idFirst == null || idSecond == null){
            throw new ValidationException("User with idFirst = null or idSecond = null");
        }
        if(userStorage.getUser(idFirst) == null || userStorage.getUser(idSecond) == null){
            throw new UserNotFoundException("First or second user not found");
        }
        userStorage.addFriend(idFirst, idSecond);
        return userStorage.getUser(idFirst);
    }

    public User deleteFriend(Integer idUser, Integer idDeleteFriend){
        if(idUser == null || idDeleteFriend == null){
            throw new ValidationException("User with idFirst = null or idSecond = null");
        }
        if(userStorage.getUser(idUser) == null || userStorage.getUser(idDeleteFriend) == null){
            throw new UserNotFoundException("First or second user not found");
        }
        userStorage.deleteFriend(idUser, idDeleteFriend);
        return userStorage.getUser(idUser);
    }

    public Collection<User> showListFriends(Integer id){
        if(id == null){
            throw new ValidationException("User id = null");
        }
        if(userStorage.getUser(id) == null){
            throw new UserNotFoundException("User not found");
        }
        return userStorage.getFriends(id);
    }

    public Collection<User> showJoinListFriends(Integer idFirstUser, Integer idSecondUser){
        if(idFirstUser == null || idSecondUser == null){
            throw new ValidationException("User with idFirst = null or idSecond = null");
        }
        if(userStorage.getUser(idFirstUser) == null || userStorage.getUser(idSecondUser) == null){
            throw new UserNotFoundException("First or second user not found");
        }
        return userStorage.getJoinListFriends(idFirstUser, idSecondUser);
    }

    public User getUser(Integer id){
        if(id == null){
            throw new ValidationException("User id = null");
        }
        if (userStorage.getUser(id) == null){
            throw new UserNotFoundException("User not found");
        }
        return userStorage.getUser(id);
    }

    public Collection<User> getUsers(){
        return userStorage.getUsers();
    }

    public User createUser(User user){
        if(Validate.isValidUser(user)) {
            if(user.getName() == null || user.getName() == ""){
                user.setName(user.getLogin());
            }
            log.info("User added: {}", user.toString());
            return userStorage.createUser(user);
        } else {
            log.error("Error input data. Check request");
            throw new ValidationException("Error input data. Check request");
        }

    }

    public User updateUser(User user){
        return userStorage.updateUser(user);
    }
}