package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController("/films")
public class FilmController {

    private static final LocalDate DATE_FIRST_FILM = LocalDate.of(1985, 12, 28);

    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping("/get-films")
    public Collection<Film> getFilms(){
        return films.values();
    }

    @PostMapping("/add-film")
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

    @PutMapping("/update-film")
    public Film updateFilm(@RequestBody Film film){
        films.put(film.getId(), film);
        log.info("Обновлен фильм: {}", film.toString());
        return film;
    }

}
