package com.neneth.oskar_game.services;

import com.neneth.oskar_game.models.Dtos.ConnectRoomDto;
import com.neneth.oskar_game.models.Dtos.CreateRoomDto;
import com.neneth.oskar_game.models.Messages.WebSocketMessage;
import com.neneth.oskar_game.models.Player;
import com.neneth.oskar_game.models.Room;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Getter
public class RoomService {
    private List<Room> rooms = new ArrayList<>();

    public Room createRoom(final WebSocketMessage message) {
        final Player currentPlayer = new Player(
                message.getClientId(),
                message.getPseudo(),
                true,
                false
        );
        final Room room = new Room(
                UUID.randomUUID().toString(),
                new java.util.Date(),
                message.getPseudo(),
                List.of(currentPlayer),
                false
        );
        this.rooms.add(room);
        return room;
    }

    public Room joinRoom(final WebSocketMessage message) {
        Room room = this.getRoom(message.getRoomId());

        if(room.isInProgress()) {
            throw new IllegalStateException("Cannot join a room that is already in progress");
        }

        final Optional<Player> player = room.getPlayers().stream().filter(p -> p.getClientId().equals(message.getClientId())).findFirst();

        if (player.isPresent()) {
            return room;
        }

        final Player currentPlayer = new Player(
                message.getClientId(),
                message.getPseudo(),
                false,
                false
        );

        room.addPlayer(currentPlayer);
        return room;
    }

    public Room playerCancelReady(final WebSocketMessage message) {
        final Room room = this.getRoom(message.getRoomId());
        final Player player = this.getPlayer(room, message.getClientId());

        player.setReady(false);

        return room;
    }

    public Room playerReady(final WebSocketMessage message) {
        final Room room = this.getRoom(message.getRoomId());
        final Player player = this.getPlayer(room, message.getClientId());

        player.setReady(true);

        return room;
    }

    public Room startGame(final WebSocketMessage message) {
        final Room room = this.getRoom(message.getRoomId());
        final Player player = this.getPlayer(room, message.getClientId());

        room.setInProgress(true);

        return room;
    }

    public Room getRoom(final String roomId) {
        return this.rooms.stream()
                .filter(r -> r.getId().equals(roomId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
    }


    public void removePlayer(final String roomId, final String clientId) {
        final Room room = this.getRoom(roomId);

        room.getPlayers().removeIf(p -> p.getClientId().equals(clientId));
    }

    private Player getPlayer(final Room room, final String clientId) {
        return room.getPlayers().stream()
                .filter(p -> p.getClientId().equals(clientId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Player not found in room"));
    }
}
