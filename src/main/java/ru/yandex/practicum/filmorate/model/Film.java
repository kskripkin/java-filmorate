package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Film{
    private int id;
    private String name;
    private String description;
    private int genre;
    private LocalDate releaseDate;
    private int duration;
    private int rate;

    public Film(Integer id, String name, String description, int genre, LocalDate releaseDate, int duration, int rate) {
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
}
