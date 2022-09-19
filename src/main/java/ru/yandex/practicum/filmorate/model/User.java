package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class User {
    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private Set<Long> friends;

    public void setFriend(long id){
        friends.add(id);
    }

    public void deleteFriend(long id){
        friends.remove(id);
    }
}
