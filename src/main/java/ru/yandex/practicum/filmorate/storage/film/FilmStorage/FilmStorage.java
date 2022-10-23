package ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    /**
     * @return набор фильмов для отдачи его клиенту
     */
    Collection<Film> getFilms();

    /**
     * @param film принимает обьект film и добавляет в хранилище фильмов
     * @return
     */
    Film addFilm(Film film);

    /**
     * @param film принимает обьект film, ищет его в хранилище и если он найден, то обновляет данные по нему
     * @return
     */
    Film updateFilm(Film film);

    void likeFilm(Integer filmId, Integer userId);

    void deleteLike(Integer filmId, Integer userId);

    Collection<Film> getTopFilms(Integer count);

    Film getFilm(Integer id);
}
