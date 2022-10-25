package ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

@Component
public class UserDbStorage implements UserStorage{

    private final JdbcTemplate jdbcTemplate;

    private KeyHolder keyHolder;

    public UserDbStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public Collection<User> getUsers() {
        String sqlQuery = "select * from users";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeUser(rs));
    }

    private User makeUser(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("user_id");
        String email = rs.getString("email");
        String login = rs.getString("login");
        String name = rs.getString("name");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        return new User(id, email, login, name, birthday);
    }

    @Override
    public User createUser(User user) {
        String sqlQuery = "insert into users(email, login, name, birthday) " +
                "values (?, ?, ?, ?)";
        keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"user_id"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);
        return this.getUser(keyHolder.getKey().intValue());
    }

    @Override
    public User updateUser(User user) {
        String sqlQuery = "update users set " +
                "email = ?, login = ?, name = ?, birthday = ? " +
                "where user_id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"user_id"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            stmt.setInt(5, user.getId());
            return stmt;
        }, keyHolder);
        return this.getUser(keyHolder.getKey().intValue());
    }

    @Override
    public User getUser(Integer id){
        String sqlQuery = "select * from users where user_id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeUser(rs), id);
    }

    @Override
    public void addFriend(Integer id, Integer friendId){
        String sqlQuery = "insert into friends(user_id, friend_id) " +
                "values (?, ?)";
        jdbcTemplate.update(sqlQuery,
                id,
                friendId
        );
        sqlQuery = "insert into friends(friend_id, user_id) " +
                "values (?, ?)";
        jdbcTemplate.update(sqlQuery,
                id,
                friendId
        );
    }

    @Override
    public void deleteFriend(Integer id, Integer friendId){
        String sqlQuery = "delete from friends where user_id = ? and friend_id = ?";
        jdbcTemplate.update(sqlQuery,
                id,
                friendId
        );
        sqlQuery = "delete from friends where user_id = ? and friend_id = ?";
        jdbcTemplate.update(sqlQuery,
                friendId,
                id
        );
    }

    @Override
    public Collection<User> getFriends(int id){
        String sqlQuery = "select users.user_id, users.email, users.login, users.name, users.birthday " +
                "from users " +
                "join friends on users.user_id = friends.user_id " +
                "where friends.friend_id = ?";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeUser(rs), id);
    }

    @Override
    public Collection<User> getJoinListFriends(int id, int otherId){
        String sqlQuery = "select * from users " +
                "where user_id in (select fr1.friend_id from friends as fr1 " +
                "join friends as fr2 on fr1.friend_id = fr2.friend_id " +
                "where fr1.user_id = ? or fr2.user_id = ?)";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeUser(rs), id, otherId);
    }
}
