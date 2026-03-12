package com.neneth.oskar_game.models;

import com.neneth.oskar_game.models.Entities.ResultEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private String clientId;
    private String pseudo;
    private boolean isAdmin;
    private boolean isReady;
    private List<MovieItem> movieItems;

    public ResultEntity.MinimizedPlayer toMinimizedPlayer(final List<Prediction> predictions) {
        return new ResultEntity.MinimizedPlayer(
                this.getClientId(),
                this.getPseudo(),
                this.getMovieItems().stream().map(item -> item.toMinimizedMovieItem(
                        predictions
                                .stream()
                                .filter(pred -> pred
                                        .getMovieItems()
                                        .stream().map(MovieItem::getMovieItemId).toList()
                                        .contains(item.getMovieItemId()))
                                .findFirst().orElseThrow(() -> new IllegalArgumentException("Prediction not found for movie item id: " + item.getMovieItemId()))
                                .getCategory_name()
                        ))
                        .toList()

        );
    }
}
