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
    private int genre;
    private LocalDate releaseDate;
    private int duration;
    private int rate;
    private Set<Long> likes = new HashSet<>();

    public Film(int id, String name, String description, int genre, LocalDate releaseDate, int duration, int rate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
    }

    public Film(String name, String description, int genre, LocalDate releaseDate, int duration, int rate) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.rate = rate;
    }

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
