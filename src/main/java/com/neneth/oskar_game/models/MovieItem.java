package com.neneth.oskar_game.models;

import com.neneth.oskar_game.models.Entities.MovieItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieItem {
    private Long movieItemId;
    private String nominee;
    private Movie movie;
    private Boolean won;

    public MovieItem createFromEntity(MovieItemEntity entity) {
        this.movieItemId = entity.getMovieItemId();
        this.nominee = entity.getNominee();
        this.movie = new Movie().createFromEntity(entity.getMovie());
        this.won = entity.getWon();

        return this;
    }
}
