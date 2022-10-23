package ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;

public interface UserStorage {

    /**
     * @return исходную мапу со всеми пользователями
     */
    Map<Integer, User> getUserSourceMap();

    /**
     * @return всех пользователей для отдачи ответа клиенту
     */
    Collection<User> getUsers();

    /**
     * @param user принимает обьект User и добавляет в хранилище пользователей
     * @return
     */
    User createUser(User user);

    /**
     * @param user принмает обьект User, ищет его в хранилище и если он найден, то обновляет данные по нему
     * @return
     */
    User updateUser(User user);

    User getUser(Integer id);

    void addFriend(Integer id, Integer friendId);

    void deleteFriend(Integer id, Integer friendId);

    Collection<User> getFriends(int id);

    Collection<User> getJoinListFriends(int id, int otherId);
}
