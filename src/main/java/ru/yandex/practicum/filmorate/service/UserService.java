package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.UserStorage;

import java.util.Collection;
import java.util.Set;

@Service
public class UserService {

    private UserStorage userStorage = new InMemoryUserStorage();

    private Collection<User> users;

    public void addFriend(Integer idFirst, Integer idSecond){
        if(idFirst == null || idSecond == null){
            throw new ValidationException("User id = null");
        }
        users = userStorage.getUsers();
        for (User user : users) {
            if (user.getId() == idFirst){
                user.setFriend(idSecond);
                userStorage.updateUser(user);
            } else if (user.getId() == idSecond){
                user.setFriend(idFirst);
                userStorage.updateUser(user);
            }
        }
        throw new UserNotFoundException(String.format(
                "User with id = %s or id = %s not found",
                idFirst, idSecond));
    }

    public void deleteFriend(Integer idUser, Integer idDeleteFriend){
        if(idUser == null || idDeleteFriend == null){
            throw new ValidationException("User id = null");
        }
        users = userStorage.getUsers();
        for (User user : users){
            if (user.getId() == idUser){
                user.deleteFriend(idDeleteFriend);
                userStorage.updateUser(user);
            } else if (user.getId() == idDeleteFriend){
                user.deleteFriend(idUser);
                userStorage.updateUser(user);
            }
        }
        throw new UserNotFoundException(String.format(
                "User with id = %s or id = %s not found",
                idUser, idDeleteFriend));
    }

    public Collection<User> showListFriends(Integer id){
        if(id == null){
            throw new ValidationException("User id = null");
        }
        users = userStorage.getUsers();
        Set<Long> idFriends = null;
        Set<User> listUsers = null;
        for (User user : users){
            if (user.getId() == id){
                idFriends = user.getFriends();
            }
        }
        if (idFriends == null){
            throw new UserNotFoundException(String.format(
                    "User with id = %s not found", id));
        }
        for (User user : users){
            for (Long idFriend : idFriends){
                if(user.getId() == idFriend){
                    listUsers.add(user);
                }
            }
        }
        return listUsers;
    }

    public Collection<User> showJoinListFriends(Integer idFirstUser, Integer idSecondUser){
        if(idFirstUser == null || idSecondUser == null){
            throw new ValidationException("User id = null");
        }
        users = userStorage.getUsers();
        Set<Long> friendsFirstUser = null;
        Set<Long> friendsSecondUser = null;
        Set<Long> joinListFriends = null;
        Set<User> finalListUser = null;

        for (User user : users){
            if (user.getId() == idFirstUser){
                friendsFirstUser = user.getFriends();
            } else if (user.getId() == idSecondUser){
                friendsSecondUser = user.getFriends();
            }
        }

        for (Long idFirst : friendsFirstUser){
            for (Long idSecond : friendsSecondUser){
                if (idFirst == idSecond){
                    joinListFriends.add(idFirst);
                }
            }
        }

        for (Long idUser : joinListFriends){
            for (User user : users){
                if (idUser == user.getId()){
                    finalListUser.add(user);
                }
            }
        }

        return finalListUser;
    }

    public User getUser(Integer id){
        if(id == null){
            throw new ValidationException("User id = null");
        }
        users = userStorage.getUsers();
        for (User user : users){
            if(user.getId() == id){
                return user;
            }
        }
        throw new UserNotFoundException(String.format(
                "User with id = %s not found",
                id));
    }


}
