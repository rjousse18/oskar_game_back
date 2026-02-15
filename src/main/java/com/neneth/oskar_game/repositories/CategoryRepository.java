package com.neneth.oskar_game.repositories;

import com.neneth.oskar_game.models.Entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    public List<CategoryEntity> findAllByYear_Year(final Integer year);
}
