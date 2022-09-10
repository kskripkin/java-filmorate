package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
public class FilmController {

    private static final LocalDate DATE_FIRST_FILM = LocalDate.of(1895, 12, 28);

    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping("/films")
    public Collection<Film> getFilms(){
        return films.values();
    }

    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film){
        if (film.getName().isBlank() ||
                film.getDescription().length() > 200 ||
                film.getReleaseDate().isBefore(DATE_FIRST_FILM) ||
                film.getDuration().getSeconds() <= 0
        ){
            log.error("Ошибка входящих данных. Проверьте переданные данные.");
            throw new ValidationException("Ошибка входящих данных. Проверьте переданные данные.");
        }
        films.put(film.getId(), film);
        log.info("Добавлен фильм: {}", film.toString());
        return film;
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
