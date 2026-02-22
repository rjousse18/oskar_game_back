package com.neneth.oskar_game.models.Messages;

import com.neneth.oskar_game.models.MovieItem;
import lombok.Data;

import java.util.List;

@Data
public class WebSocketMessage {
    private MessageType type;
    private String roomId;
    private String pseudo;
    private String clientId;
    private MovieItem movieItem; // Prediction use made.
}
