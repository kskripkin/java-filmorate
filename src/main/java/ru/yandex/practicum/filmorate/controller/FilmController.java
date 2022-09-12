package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validate.Validate;

import java.util.*;

@Slf4j
@RestController
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @GetMapping("/films")
    public Collection<Film> getFilms(){
        return films.values();
    }

    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film){
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

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film){
        if(films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Обновлен фильм: {}", film.toString());
        } else {
            log.error("Id film not found");
            throw new ValidationException("Id film not found");
        }
        return film;
    }

}
