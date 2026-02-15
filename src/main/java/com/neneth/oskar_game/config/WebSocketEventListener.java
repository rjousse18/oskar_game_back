package com.neneth.oskar_game.config;

import com.neneth.oskar_game.models.Room;
import com.neneth.oskar_game.services.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final RoomService roomService;
    private final SimpMessagingTemplate messagingTemplate;

//    @EventListener
//    public void onSubscribe(SessionSubscribeEvent event) {
//        System.out.println(event);
//    }

    @EventListener
    public void onDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String playerId = accessor.getSessionId();
        String roomId = (String) accessor.getSessionAttributes().get("roomId");

        if (roomId != null) {
            roomService.removePlayer(roomId, playerId);
            Room room = roomService.getRoom(roomId);
            if (room != null) {
                messagingTemplate.convertAndSend("/topic/room/" + roomId, room);
            }
        }
    }
}