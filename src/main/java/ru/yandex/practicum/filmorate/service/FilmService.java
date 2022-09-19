package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

@Service
public class FilmService {

    /*
    Создайте FilmService, который будет отвечать за операции с фильмами, — добавление и удаление лайка,
    вывод 10 наиболее популярных фильмов по количеству лайков. Пусть пока каждый пользователь может поставить
    лайк фильму только один раз.
     */

    public void addLike(int idFilm){

    }

    public void deleteLike(int idFilm){

    }

    public Collection<Film> show10PopularFilms(){

        return null;
    }
}
