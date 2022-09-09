package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    private FilmController filmController;
    private Film film;

    @BeforeEach
    public void BeforeEach(){
        filmController = new FilmController();
        film = new Film();
        film.setName("Терминатор");
        film.setDescription("Фильм про восстание машин");
        film.setId(1);
        film.setReleaseDate(LocalDate.of(2005, 12,11));
        film.setDuration(Duration.ofMinutes(120));
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
        film.setReleaseDate(LocalDate.of(1984, 12,11));
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            filmController.addFilm(film);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void addFilmDurationZero() {
        film.setDuration(Duration.ofMinutes(0));
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            filmController.addFilm(film);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void addFilmDurationMinusOne() {
        film.setDuration(Duration.ofMinutes(-1));
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            filmController.addFilm(film);
        });
        assertNotNull(thrown.getMessage());
    }

}