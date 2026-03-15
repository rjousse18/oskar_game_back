package com.neneth.oskar_game.repositories;

import com.neneth.oskar_game.models.Entities.MovieItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MovieItemRepository extends JpaRepository<MovieItemEntity, Long> {
    @Modifying
    @Query("""
        UPDATE movie_items m SET m.won = :won
        WHERE m.movieItemId = :movieItemId
        """)
    void updateWonByMovieItemId(Long movieItemId, boolean won);

    MovieItemEntity findByMovieItemId(Long movieItemId);
}
