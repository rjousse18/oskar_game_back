package com.neneth.oskar_game.models;

import com.neneth.oskar_game.models.Entities.MovieItemEntity;
import com.neneth.oskar_game.models.Entities.ResultEntity;
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

    public ResultEntity.MinimizedMovieItem toMinimizedMovieItem() {
        return new ResultEntity.MinimizedMovieItem(
                this.getMovieItemId(),
                this.getNominee(),
                this.getMovie()
        );
    }
}
