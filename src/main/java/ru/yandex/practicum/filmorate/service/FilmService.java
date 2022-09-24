package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.UserStorage;

import java.util.*;

@Service
public class FilmService {

    private FilmStorage filmStorage = new InMemoryFilmStorage();
 //   private UserStorage userStorage = new InMemoryUserStorage();

    /*
    Создайте FilmService, который будет отвечать за операции с фильмами, — добавление и удаление лайка,
    вывод 10 наиболее популярных фильмов по количеству лайков. Пусть пока каждый пользователь может поставить
    лайк фильму только один раз.
     */
    private Collection<Film> films;

    public Film addLike(Integer idFilm, Integer idUser){
        if(idFilm == null || idUser == null){
            throw new ValidationException("User id or film id = null");
        }
        films = filmStorage.getFilms();
        for (Film film : films){
            if (film.getId() == idFilm){
                film.setLike(idUser);
                return film;
            }
        }
        throw new FilmNotFoundException(String.format(
                "Film with id %s not found",
                idFilm));
    }

    public Film deleteLike(Integer idFilm, Integer idUser){
        if(idFilm == null || idUser == null){
            throw new ValidationException("User id or film id = null");
        }
        films = filmStorage.getFilms();
        for (Film film : films){
            if (film.getId() == idFilm){
                film.deleteLike(idUser);
                return film;
            }
        }
        throw new FilmNotFoundException(String.format(
                "Film with id %s not found",
                idFilm));
    }

    public Collection<Film> showPopularFilms(int count){
        films = filmStorage.getFilms();
        List<Film> filmsList = new ArrayList();
        filmsList.addAll(films);
        Collections.sort(filmsList);
        if ((count > filmsList.size())){
            count = filmsList.size();
        }
        return filmsList.subList(0, count);
    }

    public Film getFilm(Integer id){
        if(id == null){
            throw new ValidationException("Film id = null");
        }
        films = filmStorage.getFilms();
        for (Film film : films){
            if(film.getId() == id){
                return film;
            }
        }
        throw new FilmNotFoundException(String.format(
                "Film with id = %s not found",
                id));
    }
}
