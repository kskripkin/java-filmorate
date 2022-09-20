package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ExceptionHandler;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.UserStorage;

import java.util.Collection;
import java.util.Set;

@Service
public class UserService {

    private UserStorage userStorage = new InMemoryUserStorage();

    private Collection<User> users;

    public void addFriend(int idFirst, int idSecond){
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
    }

    public void deleteFriend(int idUser, int idDeleteFriend){
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
    }

    public Collection<User> showListFriends(int id){
        users = userStorage.getUsers();
        Set<Long> idFriends = null;
        Set<User> listUsers = null;
        for (User user : users){
            if (user.getId() == id){
                idFriends = user.getFriends();
            }
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

    public Collection<User> showJoinListFriends(int idFirstUser, int idSecondUser){
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

    public User getUser(int id){
        users = userStorage.getUsers();
        for (User user : users){
            if(user.getId() == id){
                return user;
            }
        }
        throw new ExceptionHandler();
    }


}
