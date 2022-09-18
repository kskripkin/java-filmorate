package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.InMemoryFilmStorage;

import java.util.*;

@Slf4j
@RestController
public class FilmController {

    private FilmStorage filmStorage = new InMemoryFilmStorage();

    @GetMapping("/films")
    public Collection<Film> getFilms(){
        return filmStorage.getFilms();
    }

    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film){
        return filmStorage.addFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film){
        return filmStorage.updateFilm(film);
    }

}
