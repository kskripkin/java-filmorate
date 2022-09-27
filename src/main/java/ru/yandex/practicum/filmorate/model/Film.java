package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film implements Comparable<Film>{
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Set<Long> likes = new HashSet<>();

    public void setLike(long idUser){
        likes.add(idUser);
    }

    public void deleteLike(long idUser){
        likes.remove(idUser);
    }

    @Override
    public int compareTo(Film o){
        return o.getLikes().size() - this.getLikes().size();
    }
}
