package ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class FilmDbStorage implements FilmStorage{

    private final JdbcTemplate jdbcTemplate;

    private KeyHolder keyHolder;
    private ArrayList<Genre> genreArrayList;

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
        String sqlQuery = "insert into films(name, description, rate, release_date, duration, mpa) " +
                "values (?, ?, ?, ?, ?, ?)";
        keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
                stmt.setString(1, film.getName());
                stmt.setString(2, film.getDescription());
                stmt.setInt(3, film.getRate());
                stmt.setDate(4, Date.valueOf(film.getReleaseDate()));
                stmt.setInt(5, film.getDuration());
                stmt.setInt(6, film.getMpa().getId());
                return stmt;
            }, keyHolder);


        int returnsFilmId = keyHolder.getKey().intValue();
        if (film.getGenres() != null) {
            String sqlQueryGenresFilm = "insert into genres_film(genre_id, film_id) " +
                    "values(?, ?)";
            for (int i = 0; i < film.getGenres().size(); i++) {
                    jdbcTemplate.update(sqlQueryGenresFilm, film.getGenres().get(i).getId(), returnsFilmId);
            }
        }
        return this.getFilm(returnsFilmId);
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "update films set " +
                "name = ?, description = ?, rate = ?, release_date = ?, duration = ?, mpa = ? " +
                "where film_id = ?";
        keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setInt(3, film.getRate());
            stmt.setDate(4, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(5, film.getDuration());
            stmt.setInt(6, film.getMpa().getId());
            stmt.setInt(7, film.getId());
            return stmt;
        }, keyHolder);

        int returnsFilmId = keyHolder.getKey().intValue();
        if (film.getGenres() != null) {
            String sqlQueryDeleteGenresFilm = "delete from genres_film where film_id = ?";
            jdbcTemplate.update(sqlQueryDeleteGenresFilm, returnsFilmId);
            String sqlQueryGenresFilm = "insert into genres_film(genre_id, film_id) " +
                "values(?, ?)";
            for (int i = 0; i < film.getGenres().size(); i++) {
                int temp = jdbcTemplate.queryForObject("select count(*) from genres_film where genre_id = ? and film_id = ?",
                        new Object[]{film.getGenres().get(i).getId(), returnsFilmId}, Integer.class);
                if (temp == 0) {
                    jdbcTemplate.update(sqlQueryGenresFilm, film.getGenres().get(i).getId(), returnsFilmId);
                }
            }
        }
        return this.getFilm(returnsFilmId);
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
                "left join likes as lk on f.film_id = lk.film_id " +
                "group by f.film_id " +
                "order by count(lk.film_id) desc " +
                "limit ?";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeFilm(rs), count);
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
    public Collection<Genre> getGenres() {

        String sqlQuery = "select * from genres order by genre_id";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeGenre(rs));
    }

    @Override
    public Genre getGenres(Integer id) {
        String sqlQuery = "select * from genres where genre_id = ? ";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeGenre(rs), id);
    }

    private Genre makeGenre(ResultSet rs) throws SQLException{
        Integer id = rs.getInt("genre_id");
        String name = rs.getString("category");
        return new Genre(id, name);
    }

    private Mpa makeMpa(ResultSet rs) throws SQLException{
        Integer id = rs.getInt("rating_id");
        String name = rs.getString("rating_name");
        return new Mpa(id, name);
    }

    private Collection<Genre> getGenresListId(Integer film_id) {
        String sqlQuery = "select genres.genre_id, genres.category from genres join genres_film on genres.genre_id = genres_film.genre_id where genres_film.film_id = ?";
        return jdbcTemplate.query(sqlQuery, (rsn, rowNum) -> makeGenre(rsn), film_id);
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("film_id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        Integer rate = rs.getInt("rate");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        Integer duration = rs.getInt("duration");
        Integer mpa = rs.getInt("mpa");
        genreArrayList = new ArrayList<>();
        genreArrayList.addAll(getGenresListId(id));

        return new Film(id, name, description, genreArrayList, rate, releaseDate, duration, this.getMpas(mpa));
    }
}
