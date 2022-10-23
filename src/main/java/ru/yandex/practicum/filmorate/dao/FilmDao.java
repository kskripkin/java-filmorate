package ru.yandex.practicum.filmorate.dao;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmDao {

    /**
     * @return все фильмы
     */
    Optional<Film> getFilms();

    /**
     * @param film принимает объект film и добавляет в хранилище фильмов
     * @return добавленный фильм
     */
    Optional<Film> addFilm(Film film);

    /**
     * @param film принимает обьект film и обновляет его в хранилище фильмов
     * @return обновленный фильм
     */
    Optional<Film> updateFilm(Film film);

    /**
     * @param id получение фильма по id
     * @return запрашиваемый фильм
     */
    Optional<Film> getFilm(int id);

    /**
     * @param userId принимает id пользователя и добавляет его лайк к фильму
     * @return фильм с обновленным количеством лайков
     */
    Optional<Film> likeFilm(int userId);

    /**
     * @param userId принимает id пользователя и удаляет его лайк к фильму
     * @return фильм с обновленным количеством лайков
     */
    Optional<Film> deleteLike(int userId);

    /**
     * @param count принимает количество фильмов для
     * @return
     */
    Collection<Film> getTopFilms(Integer count);
}
