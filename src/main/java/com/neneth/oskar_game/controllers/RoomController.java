package com.neneth.oskar_game.controllers;

import com.neneth.oskar_game.models.Dtos.ConnectRoomDto;
import com.neneth.oskar_game.models.Dtos.CreateRoomDto;
import com.neneth.oskar_game.models.Messages.WebSocketMessage;
import com.neneth.oskar_game.models.Room;
import com.neneth.oskar_game.services.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceAware;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Controller
public class RoomController {
    private final RoomService roomService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/game")
    public void handleMessage(
            WebSocketMessage message,
            Principal principal,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        final Room room;
//        final String playerId = principal.getName();

//        headerAccessor.getSessionAttributes().put("roomId", message.getRoomId());

        switch (message.getType()) {
            case CREATE_ROOM -> {
                room = roomService.createRoom(message);
                messagingTemplate.convertAndSend("/topic/client/" + message.getClientId(), room);
                return;
            }
            case JOIN_ROOM -> room = roomService.joinRoom(message);
            case PLAYER_READY -> room = roomService.playerReady(message);
            case PLAYER_CANCEL_READY -> room = roomService.playerCancelReady(message);
            case START_GAME -> room = roomService.startGame(message);
            default -> {
                log.warn("Unknown message type: {}", message.getType());
                throw new IllegalArgumentException("\"Unknown message type: {}\", message.getType()");
            }
        }

        messagingTemplate.convertAndSend("/topic/room/" + room.getId(), room);
    }
}