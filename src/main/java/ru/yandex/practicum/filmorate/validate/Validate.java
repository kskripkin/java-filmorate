package ru.yandex.practicum.filmorate.validate;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class Validate {

    private static final LocalDate DATE_FIRST_FILM = LocalDate.of(1895, 12, 28);

    public static boolean isValidFilm(Film film){
        if (film.getName().isBlank() ||
                film.getDescription().length() > 200 ||
                film.getReleaseDate().isBefore(DATE_FIRST_FILM) ||
                film.getDuration() <= 0
        ){
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidUser(User user){
        if(user.getEmail() == null ||
                user.getEmail().isBlank() ||
                !user.getEmail().contains("@") ||
                user.getLogin().contains(" ") ||
                user.getLogin().isBlank() ||
                user.getBirthday().isAfter(LocalDate.now())
        ) {
            return false;
        } else {
            return true;
        }
    }
}
