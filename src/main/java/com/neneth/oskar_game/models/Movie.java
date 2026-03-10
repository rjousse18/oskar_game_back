package com.neneth.oskar_game.models;

import com.neneth.oskar_game.models.Entities.MovieEntity;
import com.neneth.oskar_game.models.Entities.YearEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    private Long movieId;
    private String title;
    private String original_title;
    private Integer year;

    public Movie createFromEntity(MovieEntity movieEntity) {
        this.movieId = movieEntity.getMovieId();
        this.title = movieEntity.getTitle();
        this.original_title = movieEntity.getOriginal_title();
        this.year = movieEntity.getYear().getYear();

        return this;
    }
}
