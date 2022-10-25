package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

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
        Mpa mpa = new Mpa(1, "G");
        film = new Film("Терминатор", "Фильм про восстание машин", 1, LocalDate.of(2005, 12,11), 121, mpa);
        film1 = new Film("Терминатор2", "Фильм про восстание машин", 2, LocalDate.of(2005, 12,11), 122, mpa);
        film2 = new Film("Терминатор3", "Фильм про восстание машин", 3, LocalDate.of(2005, 12,11), 123, mpa);
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
        assertEquals(filmService.addLike(1, 1).toString(), "Film(id=1, name=Терминатор, description=Фильм про восстание машин, genre=1, releaseDate=2005-12-11, duration=121, mpa=Mpa(ratingId=1, ratingName=G))");
        assertEquals(filmService.deleteLike(1, 1).toString(), "Film(id=1, name=Терминатор, description=Фильм про восстание машин, genre=1, releaseDate=2005-12-11, duration=121, mpa=Mpa(ratingId=1, ratingName=G))");
    }

    @Order(2)
    @Test
    @Transactional
    void addAndGetFilmTest(){
        BeforeEach();
        assertEquals(filmService.getFilm(4).toString(), "Film(id=4, name=Терминатор, description=Фильм про восстание машин, genre=1, releaseDate=2005-12-11, duration=121, mpa=Mpa(ratingId=1, ratingName=G))");
    }

    @Order(3)
    @Test
    @Transactional
    void updateFilmTest(){
        BeforeEach();
        Mpa mpa = new Mpa(1, "G");
        filmService.updateFilm(new Film(7,"Титаник", "Фильм про восстание машин", 1, LocalDate.of(2005, 12,11), 121, mpa));
        assertEquals(filmService.getFilm(7).toString(), "Film(id=7, name=Титаник, description=Фильм про восстание машин, genre=1, releaseDate=2005-12-11, duration=121, mpa=Mpa(ratingId=1, ratingName=G))");
    }

    @Order(4)
    @Test
    @Transactional
    void showPopularFilmsTest(){
        BeforeEach();
        filmService.addLike(10, 10);
        filmService.addLike(12, 11);
        filmService.addLike(12, 12);

        assertEquals(filmService.showPopularFilms(3).toString(), "[Film(id=12, name=Терминатор3, description=Фильм про восстание машин, genre=3, releaseDate=2005-12-11, duration=123, mpa=Mpa(ratingId=1, ratingName=G)), Film(id=10, name=Терминатор, description=Фильм про восстание машин, genre=1, releaseDate=2005-12-11, duration=121, mpa=Mpa(ratingId=1, ratingName=G))]");
    }

    @Order(5)
    @Test
    @Transactional
    void getMpasAllTest(){
        assertEquals(filmService.getMpas().toString(), "[Mpa(ratingId=1, ratingName=G), Mpa(ratingId=5, ratingName=NC-17), Mpa(ratingId=2, ratingName=PG), Mpa(ratingId=3, ratingName=PG-13), Mpa(ratingId=4, ratingName=R)]");
    }

    @Order(6)
    @Test
    @Transactional
    void getMpasOneTest(){
        assertEquals(filmService.getMpas(1).toString(), "Mpa(ratingId=1, ratingName=G)");
    }

    @Order(7)
    @Test
    @Transactional
    void getGenresAllTest(){
        assertEquals(filmService.getGenres().toString(), "[Genre(genre_id=6, category=ACTION_MOVIE), Genre(genre_id=3, category=CARTOON), Genre(genre_id=1, category=COMEDY), Genre(genre_id=5, category=DOCUMENTARY), Genre(genre_id=2, category=DRAMA), Genre(genre_id=4, category=THRILLER)]");
    }

    @Order(8)
    @Test
    @Transactional
    void getGenressOneTest(){
        assertEquals(filmService.getGenres(1).toString(), "Genre(genre_id=1, category=COMEDY)");
    }
}
