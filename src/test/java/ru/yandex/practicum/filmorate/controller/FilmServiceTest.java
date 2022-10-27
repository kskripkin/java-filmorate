package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FilmServiceTest {

    private Film film;
    private Film film1;
    private Film film2;
    private User user;
    private User user1;
    private User user2;
    private final FilmService filmService;
    private final UserService userService;

    public void BeforeEach(){
        ArrayList<Genre> genreArrayList = new ArrayList<>();
        genreArrayList.add(new Genre(1,"qwer"));
        film = new Film("Терминатор", "Фильм про восстание машин", genreArrayList, 1, LocalDate.of(2005, 12,11), 121, new Mpa(1, "G"));
        film1 = new Film("Терминатор2", "Фильм про восстание машин",genreArrayList, 2, LocalDate.of(2005, 12,11), 122, new Mpa(1, "G"));
        film2 = new Film("Терминатор3", "Фильм про восстание машин",genreArrayList, 3, LocalDate.of(2005, 12,11), 123, new Mpa(1, "G"));
        user = new User("Vasja@yandex1.ru", "Vasja1231", "Вася1", LocalDate.of(2000, 10, 11));
        user1 = new User("Vasja@yandex2.ru", "Vasja1232", "Вася2", LocalDate.of(2000, 10, 11));
        user2 = new User("Vasja@yandex3.ru", "Vasja1233", "Вася3", LocalDate.of(2000, 10, 11));
        filmService.addFilm(film);
        filmService.addFilm(film1);
        filmService.addFilm(film2);
        userService.createUser(user);
        userService.createUser(user1);
        userService.createUser(user2);
    }

    @Order(1)
    @Test
    @Transactional
    void addAndDeleteLikeTest(){
        BeforeEach();
        assertEquals(filmService.addLike(1, 1).toString(), "Film(id=1, name=Терминатор, description=Фильм про восстание машин, rate=1, genres=[Genre(id=1, name=Комедия)], releaseDate=2005-12-11, duration=121, mpa=Mpa(id=1, name=G))");
        assertEquals(filmService.deleteLike(1, 1).toString(), "Film(id=1, name=Терминатор, description=Фильм про восстание машин, rate=1, genres=[Genre(id=1, name=Комедия)], releaseDate=2005-12-11, duration=121, mpa=Mpa(id=1, name=G))");
    }

    @Order(2)
    @Test
    @Transactional
    void addAndGetFilmTest(){
        BeforeEach();
        assertEquals(filmService.getFilm(4).toString(), "Film(id=4, name=Терминатор, description=Фильм про восстание машин, rate=1, genres=[Genre(id=1, name=Комедия)], releaseDate=2005-12-11, duration=121, mpa=Mpa(id=1, name=G))");
    }

    @Order(3)
    @Test
    @Transactional
    void updateFilmTest(){
        BeforeEach();
        ArrayList<Genre> genreArrayList = new ArrayList<>();
        genreArrayList.add(new Genre(1,"qwer"));
        filmService.updateFilm(new Film(7,"Титаник", "Фильм про восстание машин", genreArrayList,1, LocalDate.of(2005, 12,11), 121, new Mpa(1, "G")));
        assertEquals(filmService.getFilm(7).toString(), "Film(id=7, name=Титаник, description=Фильм про восстание машин, rate=1, genres=[Genre(id=1, name=Комедия)], releaseDate=2005-12-11, duration=121, mpa=Mpa(id=1, name=G))");
    }

    @Order(4)
    @Test
    @Transactional
    void showPopularFilmsTest(){
        BeforeEach();
        filmService.addLike(10, 10);
        filmService.addLike(12, 11);
        filmService.addLike(12, 12);

        assertEquals(filmService.showPopularFilms(3).toString(), "[Film(id=12, name=Терминатор3, description=Фильм про восстание машин, rate=3, genres=[Genre(id=1, name=Комедия)], releaseDate=2005-12-11, duration=123, mpa=Mpa(id=1, name=G)), Film(id=10, name=Терминатор, description=Фильм про восстание машин, rate=1, genres=[Genre(id=1, name=Комедия)], releaseDate=2005-12-11, duration=121, mpa=Mpa(id=1, name=G)), Film(id=11, name=Терминатор2, description=Фильм про восстание машин, rate=2, genres=[Genre(id=1, name=Комедия)], releaseDate=2005-12-11, duration=122, mpa=Mpa(id=1, name=G))]");
    }
}
