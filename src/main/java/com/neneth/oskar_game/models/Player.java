package com.neneth.oskar_game.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Player {
    private String clientId;
    private String pseudo;
    private boolean isAdmin;
    private boolean isReady;
}
