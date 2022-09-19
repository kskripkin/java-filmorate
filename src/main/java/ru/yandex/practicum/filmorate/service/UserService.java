package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserService {

    /*
    Создайте UserService, который будет отвечать за такие операции с пользователями, как добавление в друзья,
    удаление из друзей, вывод списка общих друзей. Пока пользователям не надо одобрять заявки в друзья — добавляем сразу.
    То есть если Лена стала другом Саши, то это значит, что Саша теперь друг Лены.
     */

    public void addFriend(){

    }

    public void deleteFriend(){

    }

    public Collection<User> showListFriends(int id){

        return null;
    }

    public Collection<User> showJoinListFriends(int idFirst, int idSecond){

        return null;
    }
}
