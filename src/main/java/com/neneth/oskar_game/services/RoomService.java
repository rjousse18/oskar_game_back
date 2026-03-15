package com.neneth.oskar_game.services;

import com.neneth.oskar_game.models.Dtos.ConnectRoomDto;
import com.neneth.oskar_game.models.Dtos.CreateRoomDto;
import com.neneth.oskar_game.models.Entities.ResultEntity;
import com.neneth.oskar_game.models.Messages.WebSocketMessage;
import com.neneth.oskar_game.models.MovieItem;
import com.neneth.oskar_game.models.Player;
import com.neneth.oskar_game.models.Prediction;
import com.neneth.oskar_game.models.Room;
import com.neneth.oskar_game.repositories.CategoryRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;
import java.util.random.RandomGenerator;

@Service
@RequiredArgsConstructor
@Getter
public class RoomService {
    private final CategoryService categoryService;
    private final ResultService resultService;
    private List<Room> rooms = new ArrayList<>();
    private static final String ADMIN_PSEUDO = "ADMIN_ADMIN_ADMIN_PSEUDO";

    public Room createRoom(final WebSocketMessage message) {
        final Player currentPlayer = new Player(
                message.getClientId(),
                message.getPseudo(),
                true,
                false,
                new ArrayList<>()
        );
        final Room room = new Room(
                message.getPseudo().equals(ADMIN_PSEUDO) ? "ROOM_ADMIN" : RandomStringUtils.random(6, 0, 0, true, true, null, new SecureRandom()).toUpperCase(),
                new java.util.Date(),
                message.getPseudo(),
                List.of(currentPlayer),
                message.getPseudo().equals(ADMIN_PSEUDO),
                categoryService.getAllCategoriesThisYearAsPrediction(new GregorianCalendar().get(Calendar.YEAR)),
                0
        );

        if(room.getId().equals("ROOM_ADMIN")) {
            this.rooms.remove(this.rooms.stream().filter(r -> r.getId().equals("ROOM_ADMIN")).findFirst().orElse(null));
        }

        this.rooms.add(room);
        return room;
    }

    public Room joinRoom(final WebSocketMessage message) {
        Room room = this.getRoom(message.getRoomId());

        final Optional<Player> player = room.getPlayers().stream().filter(p -> p.getPseudo().equals(message.getPseudo())).findFirst();

        if(room.isInProgress()) {
            if(room.getPredictions().size() == room.getStep() && player.isPresent()) { // The game is over and the player exist in it, we can let him join to see the results
                return room;
            }
            throw new IllegalStateException("Cannot join a room that is already in progress");
        }

        if (player.isPresent()) {
            return room;
        }

        final Player currentPlayer = new Player(
                message.getClientId(),
                message.getPseudo(),
                false,
                false,
                new ArrayList<>()
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
        if(!player.isAdmin()) {
            throw new IllegalCallerException("Player not authorized to start the game");
        }

        if(!room.getPlayers().stream().filter(p -> !p.isReady() && !p.isAdmin()).toList().isEmpty()) {
            throw new IllegalStateException("Cannot start the game if not all players are ready");
        }

        room.setInProgress(true);
        room.setStep(0); // Safety, but should be already at 0

        return room;
    }

    public Room addPrediction(final WebSocketMessage message) {
        if(message.getMovieItem() == null) {
            throw new IllegalStateException("Cannot add predictions if no movie item given");
        }

        final Room room = this.getRoom(message.getRoomId());
        final Prediction currenPrediction = room.getPredictions().get(room.getStep());

        if(!currenPrediction.getMovieItems().contains(message.getMovieItem())) {
            throw new IllegalStateException("Cannot add predictions if movie items are not in the list");
        }

        final Player currentPlayer = this.getPlayer(room, message.getClientId());

        if(currentPlayer.getMovieItems().stream().map(MovieItem::getMovieItemId).toList().contains(message.getMovieItem().getMovieItemId())) {
            throw new IllegalArgumentException("Cannot add predictions if movie item is already in the player's list");
        }

        currentPlayer.getMovieItems().add(message.getMovieItem());

        if(isAllPlayersPredictedCurrent(room, currenPrediction)) {
            room.setStep(room.getStep() + 1);
        }

        if(room.getStep() == room.getPredictions().size()) { //
            final ResultEntity result = resultService.saveFromRoom(room);
            if(result == null) {
                throw new IllegalStateException("Cannot save results !!");
            }
        }
        return room;
    }


    public Boolean isAllPlayersPredictedCurrent(final Room room, final Prediction currentPrediction) {
        return room
                .getPlayers()
                .stream()
                .filter(p ->
                        !currentPrediction
                                .getMovieItems()
                                .stream()
                                .filter(movieItem -> p
                                        .getMovieItems()
                                        .stream()
                                        .map(MovieItem::getMovieItemId)
                                        .toList()
                                        .contains(movieItem.getMovieItemId())).toList().isEmpty()
                ).toList().size() == room.getPlayers().size();
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
