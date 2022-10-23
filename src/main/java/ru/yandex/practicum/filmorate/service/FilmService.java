package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.UserStorage;

import java.util.*;

@Service
//@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public FilmService(FilmStorage filmStorage, UserStorage userStorage){
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film addLike(Integer idFilm, Integer idUser){
        if(idFilm == null || idUser == null){
            throw new ValidationException("User id or film id = null");
        }
        if(filmStorage.getFilm(idFilm) == null){
            throw new FilmNotFoundException("Film not found");
        }
        if(userStorage.getUser(idUser) == null){
            throw new UserNotFoundException("User not found");
        }
        filmStorage.likeFilm(idFilm, idUser);
        return filmStorage.getFilm(idFilm);
    }

    public Film deleteLike(Integer idFilm, Integer idUser){
        if(idFilm == null || idUser == null){
            throw new ValidationException("User id or film id = null");
        }
        if(filmStorage.getFilm(idFilm) == null){
            throw new FilmNotFoundException("Film not found");
        }
        if(userStorage.getUser(idUser) == null){
            throw new UserNotFoundException("User not found");
        }
        filmStorage.deleteLike(idFilm, idUser);
        return filmStorage.getFilm(idFilm);
    }

    public Collection<Film> showPopularFilms(int count){
        return filmStorage.getTopFilms(count);
    }

    public Film getFilm(Integer idFilm){
        if(idFilm == null){
            throw new ValidationException("Film id = null");
        }
        if(filmStorage.getFilm(idFilm) == null){
            throw new FilmNotFoundException("Film not found");
        }
        return filmStorage.getFilm(idFilm);
    }

    public Collection<Film> getFilms(){
        return filmStorage.getFilms();
    }

    public Film addFilm(Film film){
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film){
        if(filmStorage.getFilm(film.getId()) == null){
            throw new FilmNotFoundException("Film not found");
        }
        return filmStorage.updateFilm(film);
    }
}
