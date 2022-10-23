package ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@Slf4j
@Qualifier
public class FilmDbStorage implements FilmStorage{

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public Map<Integer, Film> getFilmSourceMap() {
        return null;
    }

    @Override
    public Collection<Film> getFilms() {
        String sqlQuery = "select * from films";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeFilm(rs));
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("film_id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        Integer genre = rs.getInt("genre");
        LocalDate releaseDate = rs.getDate("releaseDate").toLocalDate();
        Integer duration = rs.getInt("duration");
        Integer rating = rs.getInt("rating");
        return new Film(id, name, description, genre, releaseDate, duration, rating);
    }
    @Override
    public Film addFilm(Film film) {
        String sqlQuery = "insert into films(name, description, genre, releaseDate, duration, rating) " +
                "values (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getGenre(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate()
        );
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "update films set " +
                "name = ?, description = ?, genre = ?, releaseDate = ?, duration = ?, rating = ? " +
                "where film_id = ?";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getGenre(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRate(),
                film.getId()
        );
        return this.getFilm(film.getId());
    }

    public Film getFilm(int id){
        String sqlQuery = "select * from films where film_id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeFilm(rs), id);
    }

    public void likeFilm(int filmid, int userId){
        String sqlQuery = "insert into likes(film_id, user_id) " +
                            "values(?, ?)";
        jdbcTemplate.update(sqlQuery,
                filmid,
                userId
        );
    }

    public void deleteLike(int filmid, int userId){
        String sqlQuery = "delete from likes where film_id = ? and user_id = ?";
        jdbcTemplate.update(sqlQuery,
                filmid,
                userId
        );
    }

    public Collection<Film> getTopFilms(Integer count){
        String sqlQuery = "select f.* from films as f " +
                "join likes as lk on f.film_id = lk.film_id " +
                "group by lk.film_id " +
                "order by count(lk.film_id) desc " +
                "limit ?";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeFilm(rs), count);
    }
}
