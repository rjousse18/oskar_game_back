package com.neneth.oskar_game.repositories;

import com.neneth.oskar_game.models.Entities.CategoryEntity;
import com.neneth.oskar_game.models.Entities.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findAllByYear_YearOrderByCategoryIdDesc(Integer yearYear);

    @Query(nativeQuery = true, value = """
            SELECT 
                c.category_id as categoryId,
                c.name as categoryName,
                m.movie_item_id as winnerMovieItemId
            FROM categories c
               INNER JOIN movie_items m ON c.category_id = m.category_id
               WHERE m.won IS TRUE
            """)
    List<ResultEntity.PredictionResult> findAllWithWinner();
}
