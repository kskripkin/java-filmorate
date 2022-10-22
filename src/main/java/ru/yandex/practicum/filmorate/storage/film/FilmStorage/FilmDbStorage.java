package ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Component
@Qualifier
public class FilmDbStorage implements FilmStorage{
    @Override
    public Map<Integer, Film> getFilmSourceMap() {
        return null;
    }

    @Override
    public Collection<Film> getFilms() {
        return null;
    }

    @Override
    public Film addFilm(Film film) {
        return null;
    }

    @Override
    public Film updateFilm(Film film) {
        return null;
    }
}
