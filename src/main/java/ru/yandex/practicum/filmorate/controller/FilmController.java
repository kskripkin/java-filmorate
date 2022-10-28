package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping("/films")
    public Collection<Film> getFilms(){
        log.info("GET /films");
        return filmService.getFilms();
    }

    @PostMapping("/films")
    public Film addFilm(@RequestBody Film film){
        log.info("POST /films");
        return filmService.addFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film){
        log.info("PUT /films");
        return filmService.updateFilm(film);
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

    @GetMapping("/genres")
    public Collection<Genre> getGenres(){
        return filmService.getGenres();
    }

    @GetMapping("/genres/{id}")
    public Genre getGenres(@PathVariable int id){
        return filmService.getGenres(id);
    }

    @GetMapping("/mpa")
    public Collection<Mpa> getMpas(){
        return filmService.getMpas();
    }

    @GetMapping("/mpa/{id}")
    public Mpa getMpas(@PathVariable int id){
        return filmService.getMpas(id);
    }
}
