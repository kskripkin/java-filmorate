package ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

@Component
public class FilmDbStorage implements FilmStorage{

    private final JdbcTemplate jdbcTemplate;

    private KeyHolder keyHolder;

    public FilmDbStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public Collection<Film> getFilms() {
        String sqlQuery = "select * from films";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public Film addFilm(Film film) {
        String sqlQuery = "insert into films(name, description, genre, release_date, duration, rating) " +
                "values (?, ?, ?, ?, ?, ?)";
        keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
                stmt.setString(1, film.getName());
                stmt.setString(2, film.getDescription());
                stmt.setInt(3, film.getGenre());
                stmt.setDate(4, Date.valueOf(film.getReleaseDate()));
                stmt.setInt(5, film.getDuration());
                stmt.setInt(6, film.getMpa().getRatingId());
                return stmt;
            }, keyHolder);
        return this.getFilm(keyHolder.getKey().intValue());
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "update films set " +
                "name = ?, description = ?, genre = ?, release_date = ?, duration = ?, rating = ? " +
                "where film_id = ?";
        keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setInt(3, film.getGenre());
            stmt.setDate(4, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(5, film.getDuration());
            stmt.setInt(6, film.getMpa().getRatingId());
            stmt.setInt(7, film.getId());
            return stmt;
        }, keyHolder);
        return this.getFilm(keyHolder.getKey().intValue());
    }

    @Override
    public Film getFilm(Integer id){
        String sqlQuery = "select * from films where film_id = ?";
        Film film;
        try {
            film = jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeFilm(rs), id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
        return film;
    }

    @Override
    public Collection<Genre> getGenres() {
        String sqlQuery = "select * from genres";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeGenre(rs));
    }

    @Override
    public Genre getGenres(Integer id) {
        String sqlQuery = "select * from genres where genre_id = ? ";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeGenre(rs), id);
    }

    @Override
    public Collection<Mpa> getMpas() {
        String sqlQuery = "select * from mpa";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeMpa(rs));
    }

    @Override
    public Mpa getMpas(Integer id) {
        String sqlQuery = "select * from mpa where rating_id = ? ";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeMpa(rs), id);
    }

    @Override
    public void likeFilm(Integer filmId, Integer userId){
        String sqlQuery = "insert into likes(film_id, user_id) " +
                            "values(?, ?)";
        jdbcTemplate.update(sqlQuery,
                filmId,
                userId
        );
    }

    @Override
    public void deleteLike(Integer filmId, Integer userId){
        String sqlQuery = "delete from likes where film_id = ? and user_id = ?";
        jdbcTemplate.update(sqlQuery,
                filmId,
                userId
        );
    }

    @Override
    public Collection<Film> getTopFilms(Integer count){
        String sqlQuery = "select f.* from films as f " +
                "join likes as lk on f.film_id = lk.film_id " +
                "group by lk.film_id " +
                "order by count(lk.film_id) desc " +
                "limit ?";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeFilm(rs), count);
    }

    private Genre makeGenre(ResultSet rs) throws SQLException{
        Integer genreId = rs.getInt("genre_id");
        String category = rs.getString("category");
        return new Genre(genreId, category);
    }

    private Mpa makeMpa(ResultSet rs) throws SQLException{
        Integer ratingId = rs.getInt("rating_id");
        String ratingName = rs.getString("rating_name");
        return new Mpa(ratingId, ratingName);
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("film_id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        Integer genre = rs.getInt("genre");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        Integer duration = rs.getInt("duration");
        Integer rating = rs.getInt("rating");
        String sqlQuery = "select * from mpa where rating_id = ?";
        Mpa mpa = jdbcTemplate.queryForObject(sqlQuery, (rsn, rowNum) -> makeMpa(rsn), rating);
        return new Film(id, name, description, genre, releaseDate, duration, mpa);
    }
}
