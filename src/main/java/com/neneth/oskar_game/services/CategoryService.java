package com.neneth.oskar_game.services;

import com.neneth.oskar_game.models.Entities.CategoryEntity;
import com.neneth.oskar_game.models.Movie;
import com.neneth.oskar_game.models.MovieItem;
import com.neneth.oskar_game.models.Player;
import com.neneth.oskar_game.models.Prediction;
import com.neneth.oskar_game.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Prediction> getAllCategoriesThisYearAsPrediction(final Integer year) {
        List<CategoryEntity> categories = this.categoryRepository.findAllByYear_Year(year);

        return categories.stream().map(category -> new Prediction(
            category.getName(),
            category.getCategoryId(),
            category.getMovieItems().stream().map(movieItem -> new MovieItem(
                    movieItem.getMovieItemId(),
                    movieItem.getNominee(),
                    new Movie(
                            movieItem.getMovie().getMovieId(),
                            movieItem.getMovie().getTitle(),
                            movieItem.getMovie().getYear().getYear()
                    ),
                    new ArrayList<>()
            )).toList()
        )).toList();
    }
}
