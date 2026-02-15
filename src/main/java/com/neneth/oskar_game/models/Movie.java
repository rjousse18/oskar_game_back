package com.neneth.oskar_game.models;

import com.neneth.oskar_game.models.Entities.YearEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class Movie {
    private final Long movieId;
    private final String title;
    private final Integer year;
}
