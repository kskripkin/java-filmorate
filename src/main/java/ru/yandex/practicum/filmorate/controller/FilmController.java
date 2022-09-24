package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.InMemoryFilmStorage;

import javax.websocket.server.PathParam;
import java.util.*;

@Slf4j
@RestController
public class FilmController {

    private FilmStorage filmStorage = new InMemoryFilmStorage();
    private FilmService filmService = new FilmService();

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

    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable int id){
        return filmService.getFilm(id);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public Film likeFilm(@PathVariable int id, @PathVariable int userId){
        return filmService.addLike(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public Film deleteLike(@PathVariable int id, @PathVariable int userId){
        return filmService.deleteLike(id, userId);
    }

    @GetMapping("/films/popular")
    public Collection<Film> getTopFilms(@RequestParam(required = false) Integer count){
        if (count == null){
            count = 10;
        }
        return filmService.showPopularFilms(count);
    }

}
