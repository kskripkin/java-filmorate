package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.UserStorage;

import java.util.Collection;
import java.util.Set;

@Service
public class FilmService {

    private FilmStorage filmStorage = new InMemoryFilmStorage();
    private UserStorage userStorage = new InMemoryUserStorage();

    /*
    Создайте FilmService, который будет отвечать за операции с фильмами, — добавление и удаление лайка,
    вывод 10 наиболее популярных фильмов по количеству лайков. Пусть пока каждый пользователь может поставить
    лайк фильму только один раз.
     */
    private Collection<Film> films;

    public void addLike(int idFilm, int idUser){
        films = filmStorage.getFilms();
        for (Film film : films){
            if (film.getId() == idFilm){
                film.setLike(idUser);
            }
        }
    }

    public void deleteLike(int idFilm, int idUser){
        films = filmStorage.getFilms();
        for (Film film : films){
            if (film.getId() == idFilm){
                film.deleteLike(idUser);
            }
        }
    }

    public Collection<Film> show10PopularFilms(){
        films = filmStorage.getFilms();
        //films.stream().sorted()
        return null;
    }
}
