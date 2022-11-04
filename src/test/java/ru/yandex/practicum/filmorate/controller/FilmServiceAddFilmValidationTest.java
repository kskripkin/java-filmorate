package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmServiceAddFilmValidationTest {

    private Film film;
    private final FilmService filmService;

    @BeforeEach
    public void BeforeEach(){
        ArrayList<Genre> genreArrayList = new ArrayList<>();
        genreArrayList.add(new Genre(1, "q"));
        film = new Film(1, "Терминатор", "Фильм про восстание машин", genreArrayList, 1, LocalDate.of(2005, 12,11), 120, new Mpa(1, "G"));
    }

    @Test
    void addFilmNameIsBlanc() throws ValidationException {
        film.setName("");
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            filmService.addFilm(film);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void addFilmDescription201chars() {
        film.setDescription("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            filmService.addFilm(film);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void addFilmDescription202chars() {
        film.setDescription("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            filmService.addFilm(film);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void addFilmDateAfterFirstFilm() {
        film.setReleaseDate(LocalDate.of(1884, 12,11));
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            filmService.addFilm(film);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void addFilmDurationZero() {
        film.setDuration(0);
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            filmService.addFilm(film);
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    void addFilmDurationMinusOne() {
        film.setDuration(-1);
        Throwable thrown = assertThrows(ValidationException.class, () -> {
            filmService.addFilm(film);
        });
        assertNotNull(thrown.getMessage());
    }

}