package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.UserStorage;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Service
public class UserService {

    private UserStorage userStorage = new InMemoryUserStorage();

    /*
    Создайте UserService, который будет отвечать за такие операции с пользователями, как добавление в друзья,
    удаление из друзей, вывод списка общих друзей. Пока пользователям не надо одобрять заявки в друзья — добавляем сразу.
    То есть если Лена стала другом Саши, то это значит, что Саша теперь друг Лены.
     */
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

    public Collection<Long> showListFriends(int id){
        users = userStorage.getUsers();
        for (User user : users){
            if (user.getId() == id){
                return user.getFriends();
            }
        }
        return null;
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
}
