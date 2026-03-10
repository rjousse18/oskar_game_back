package com.neneth.oskar_game.repositories;

import com.neneth.oskar_game.models.Entities.MovieItemEntity;
import com.neneth.oskar_game.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieItemRepository extends JpaRepository<MovieItemEntity, Long> {
    List<MovieItemEntity> findAllByMovieItemIdIn(List<Long> movieItemIds);
}
