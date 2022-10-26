package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@NoArgsConstructor
public class Film{
    private int id;
    private String name;
    private String description;
    private int rate;
    private ArrayList<Genre> genres;
    private LocalDate releaseDate;
    private int duration;
    private Mpa mpa;

    public Film(int id, String name, String description, ArrayList<Genre> genres, int rate, LocalDate releaseDate, int duration, Mpa mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rate = rate;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
    }

    public Film(String name, String description, ArrayList<Genre> genres, int rate, LocalDate releaseDate, int duration, Mpa mpa) {
        this.name = name;
        this.description = description;
        this.rate = rate;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
    }


    public Mpa getMpa() {
        return mpa;
    }

    public void setMpa(Mpa mpa) {
        this.mpa = mpa;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genre) {
        this.genres = genre;
    }
}
