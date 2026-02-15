package com.neneth.oskar_game.models.Messages;

import lombok.Data;

@Data
public class WebSocketMessage {
    private MessageType type;
    private String roomId;
    private String pseudo;
    private String clientId;
}
