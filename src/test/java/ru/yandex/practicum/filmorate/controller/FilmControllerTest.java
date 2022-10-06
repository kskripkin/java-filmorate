package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    private FilmController filmController;
    private Film film;
    private FilmStorage filmStorage;
    private FilmService filmService;
    private UserStorage userStorage;

    @BeforeEach
    public void BeforeEach(){
        userStorage = new InMemoryUserStorage();
        filmStorage = new InMemoryFilmStorage();
        filmService = new FilmService(filmStorage, userStorage);
        filmController = new FilmController(filmStorage, filmService);
        film = new Film();
        film.setName("Терминатор");
        film.setDescription("Фильм про восстание машин");
        film.setId(1);
        film.setReleaseDate(LocalDate.of(2005, 12,11));
        film.setDuration(120);
    }

    @Test
    void addFilmNameIsBlanc() throws ValidationException {
        film.setName("");
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            filmController.addFilm(film);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void addFilmDescription201chars() {
        film.setDescription("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            filmController.addFilm(film);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void addFilmDescription202chars() {
        film.setDescription("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            filmController.addFilm(film);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void addFilmDateAfterFirstFilm() {
        film.setReleaseDate(LocalDate.of(1884, 12,11));
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            filmController.addFilm(film);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void addFilmDurationZero() {
        film.setDuration(0);
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            filmController.addFilm(film);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void addFilmDurationMinusOne() {
        film.setDuration(-1);
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            filmController.addFilm(film);
        });
        assertNotNull(thrown.getMessage());
    }

}