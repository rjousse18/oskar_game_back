package com.neneth.oskar_game.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private String clientId;
    private String pseudo;
    private boolean isAdmin;
    private boolean isReady;
    private List<MovieItem> movieItems;
}
