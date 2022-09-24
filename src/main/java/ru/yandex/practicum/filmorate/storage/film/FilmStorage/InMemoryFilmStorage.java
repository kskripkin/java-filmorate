package ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validate.Validate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage{

    private final static Map<Integer, Film> films = new HashMap<>();
    private int id = 1;

    public Collection<Film> getFilms(){
        return films.values();
    }

    public Film addFilm(Film film){
        if(Validate.isValidFilm(film)) {
            if (film.getId() == 0) {
                film.setId(id++);
            }
            films.put(film.getId(), film);
            log.info("Добавлен фильм: {}", film.toString());
            return film;
        } else {
            log.error("Ошибка входящих данных. Проверьте переданные данные.");
            throw new ValidationException("Ошибка входящих данных. Проверьте переданные данные.");
        }
    }

    public Film updateFilm(Film film){
        if(films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Обновлен фильм: {}", film.toString());
        } else {
            log.error("Id film not found");
            throw new FilmNotFoundException("Id film not found");
        }
        return film;
    }
}
