package ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    /**
     * @return всех пользователей
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
}
