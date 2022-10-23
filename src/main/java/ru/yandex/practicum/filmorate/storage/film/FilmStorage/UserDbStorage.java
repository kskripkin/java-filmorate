package ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@Slf4j
@Qualifier
public class UserDbStorage implements UserStorage{

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    @Override
    public Map<Integer, User> getUserSourceMap() {
        return null;
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
                "values (?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday()
        );
        return user;
    }

    @Override
    public User updateUser(User user) {
        String sqlQuery = "update users set " +
                "email = ?, login = ?, name = ?, birthday = ? " +
                "where user_id = ?";
        jdbcTemplate.update(sqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );
        return jdbcTemplate.queryForObject(sqlQuery, (rs, rowNum) -> makeUser(rs), user.getId());
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
        String sqlQuery = "select friend_id from friends as fr " +
                            "join users as us on fr.friend_id = us.user_id " +
                            "where fr.user_id = ?";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeUser(rs), id);
    }

    @Override
    public Collection<User> getJoinListFriends(int id, int otherId){
        String sqlQuery = "select * from users " +
                "where user_id in (select friend_id from friends as fr1 " +
                "join friends as fr2 on fr1.friend_id = fr2.friend_id " +
                "where fr1.user_id = ? or fr2.user_id = ?)";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeUser(rs), id, otherId);
    }
}
