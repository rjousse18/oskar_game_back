package com.neneth.oskar_game.models.Entities;

import com.neneth.oskar_game.models.Movie;
import com.neneth.oskar_game.models.MovieItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "results")
public class ResultEntity {
    @Id
    private String roomId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "players", columnDefinition = "jsonb")
    private List<MinimizedPlayer> players;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "prediction_result", columnDefinition = "jsonb")
    private List<PredictionResult> predictionResults;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MinimizedPlayer {
        private String clientId;
        private String pseudo;
        private List<MinimizedMovieItem> movieItems;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MinimizedMovieItem {
        private Long movieItemId;
        private String nominee;
        private String categoryName;
        private Movie movie;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PredictionResult {
        private Long categoryId;
        private String categoryName;
        private Long winnerMovieItemId;
    }
}
