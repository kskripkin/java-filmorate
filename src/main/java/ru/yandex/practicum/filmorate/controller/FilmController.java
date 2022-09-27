package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.FilmStorage;

import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FilmController {

    private final FilmStorage filmStorage;
    private final FilmService filmService;

    @GetMapping("/films")
    public Collection<Film> getFilms(){
        log.info("GET /films");
        return filmStorage.getFilms();
    }

    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film){
        log.info("POST /films");
        return filmStorage.addFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film){
        log.info("PUT /films");
        return filmStorage.updateFilm(film);
    }

    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable int id){
        log.info("GET /films/{id}");
        return filmService.getFilm(id);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public Film likeFilm(@PathVariable int id, @PathVariable int userId){
        log.info("PUT /films/{id}/like/{userId}");
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public Film deleteLike(@PathVariable int id, @PathVariable int userId){
        log.info("DELETE /films/{id}/like/{userId}");
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/films/popular")
    public Collection<Film> getTopFilms(@RequestParam(defaultValue = "10", required = false) Integer count){
        log.info("GET /films/popular");
        return filmService.showPopularFilms(count);
    }

}
