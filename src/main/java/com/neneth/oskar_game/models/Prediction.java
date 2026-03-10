package com.neneth.oskar_game.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Prediction {
    private String category_name;
    private Long category_id;
    private List<MovieItem> movieItems;
}
