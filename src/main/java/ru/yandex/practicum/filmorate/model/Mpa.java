package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Mpa {
   private int ratingId;
   private String ratingName;
    /*G,
    PG,
    PG_13,
    R,
    NC_17*/
}
