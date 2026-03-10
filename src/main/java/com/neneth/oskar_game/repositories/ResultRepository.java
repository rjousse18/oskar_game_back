package com.neneth.oskar_game.repositories;

import com.neneth.oskar_game.models.Entities.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<ResultEntity, String> {
}
