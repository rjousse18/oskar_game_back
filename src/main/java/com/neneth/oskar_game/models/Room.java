package com.neneth.oskar_game.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Room {
    private String id;
    private Date createdAt;
    private String createdBy;
    private List<Player> players;
    private boolean isInProgress;

    public void addPlayer(final Player player) {
        List<Player> tempPlayers = new ArrayList<>(this.getPlayers());
        tempPlayers.add(player);
        this.setPlayers(tempPlayers);
    }
}
