package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.InMemoryFilmStorage;

import java.util.*;

@Service
public class FilmService {

    private FilmStorage filmStorage = new InMemoryFilmStorage();

    private Map<Integer,Film> films;

    public Film addLike(Integer idFilm, Integer idUser){
        if(idFilm == null || idUser == null){
            throw new ValidationException("User id or film id = null");
        }
        films = filmStorage.getFilmSourceMap();
        if(!films.containsKey(idFilm)){
            throw new FilmNotFoundException("Film not found");
        }
        films.get(idFilm).setLike(idUser);
        return films.get(idFilm);
    }

    public Film deleteLike(Integer idFilm, Integer idUser){
        if(idFilm == null || idUser == null || idUser < 0){
            throw new ValidationException("Validation exception id film or user id");
        }
        films = filmStorage.getFilmSourceMap();
        if(!films.containsKey(idFilm)){
            throw new FilmNotFoundException("Film not found");
        }
        films.get(idFilm).deleteLike(idUser);
        return films.get(idFilm);
    }

    public Collection<Film> showPopularFilms(int count){
        films = filmStorage.getFilmSourceMap();
        List<Film> filmsList = new ArrayList();
        filmsList.addAll(films.values());
        Collections.sort(filmsList);
        if ((count > filmsList.size())){
            count = filmsList.size();
        }
        return filmsList.subList(0, count);
    }

    public Film getFilm(Integer idFilm){
        if(idFilm == null){
            throw new ValidationException("Film id = null");
        }
        films = filmStorage.getFilmSourceMap();
        if(!films.containsKey(idFilm)){
            throw new FilmNotFoundException("Film not found");
        }
        return films.get(idFilm);
    }
}
