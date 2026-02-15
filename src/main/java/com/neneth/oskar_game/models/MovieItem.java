package com.neneth.oskar_game.models;

import lombok.Data;

import java.util.List;

@Data
public class MovieItem {
    private final Long movieItemId;
    private final String nominee;
    private final Movie movie;
    private final List<Player> players;
}
