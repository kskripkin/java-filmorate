package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    private Map<Integer, User> users;

    public User addFriend(Integer idFirst, Integer idSecond){
        if(idFirst == null || idSecond == null){
            throw new ValidationException("User with idFirst = null or idSecond = null");
        }
        users = userStorage.getUserSourceMap();
        if(!users.containsKey(idFirst) || !users.containsKey(idSecond)){
            throw new UserNotFoundException("First or second user not found");
        }
        users.get(idFirst).setFriend(idSecond);
        users.get(idSecond).setFriend(idFirst);
        return users.get(idFirst);
    }

    public User deleteFriend(Integer idUser, Integer idDeleteFriend){
        if(idUser == null || idDeleteFriend == null){
            throw new ValidationException("User with idFirst = null or idSecond = null");
        }
        users = userStorage.getUserSourceMap();
        if(!users.containsKey(idUser) || !users.containsKey(idDeleteFriend)){
            throw new UserNotFoundException("First or second user not found");
        }
        users.get(idUser).deleteFriend(idDeleteFriend);
        users.get(idDeleteFriend).deleteFriend(idUser);
        return users.get(idUser);
    }

    public Collection<User> showListFriends(Integer id){
        if(id == null){
            throw new ValidationException("User id = null");
        }
        users = userStorage.getUserSourceMap();
        if(!users.containsKey(id)){
            throw new UserNotFoundException("User not found");
        }
        Set<Long> idFriends = users.get(id).getFriends();
        Set<User> listUsers = new HashSet<>();
        if (idFriends.size() == 0){
            return listUsers;
        }
        for (Map.Entry<Integer, User> entry: users.entrySet()){
            for (Long idFriend : idFriends){
                if(entry.getValue().getId() == idFriend){
                    listUsers.add(entry.getValue());
                }
            }
        }
        return listUsers;
    }

    public Collection<User> showJoinListFriends(Integer idFirstUser, Integer idSecondUser){
        if(idFirstUser == null || idSecondUser == null){
            throw new ValidationException("User with idFirst = null or idSecond = null");
        }
        users = userStorage.getUserSourceMap();
        if(!users.containsKey(idFirstUser) || !users.containsKey(idSecondUser)){
            throw new UserNotFoundException("First or second user not found");
        }
        Set<Long> friendsFirstUser = users.get(idFirstUser).getFriends();
        Set<Long> friendsSecondUser = users.get(idSecondUser).getFriends();
        Set<Long> joinListFriends = new HashSet<>();
        Set<User> finalListUser = new HashSet<>();
        for (Long idFirst : friendsFirstUser){
            for (Long idSecond : friendsSecondUser){
                if (idFirst == idSecond){
                    joinListFriends.add(idFirst);
                }
            }
        }
        if(joinListFriends.size() == 0){
            return finalListUser;
        }
        for (Long idUser : joinListFriends){
            for (Map.Entry<Integer, User> entry : users.entrySet()){
                if (idUser == entry.getValue().getId()){
                    finalListUser.add(entry.getValue());
                }
            }
        }
        return finalListUser;
    }

    public User getUser(Integer id){
        if(id == null){
            throw new ValidationException("User id = null");
        }
        users = userStorage.getUserSourceMap();
        if (!users.containsKey(id)){
            throw new UserNotFoundException("User not found");
        }
        return users.get(id);
    }
}