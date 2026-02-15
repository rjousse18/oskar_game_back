package com.neneth.oskar_game.models;

import lombok.Data;

import java.util.List;

@Data
public class Prediction {
    private final String category_name;
    private final Long category_id;
    private final List<MovieItem> movieItems;
}
