package com.neneth.oskar_game.services;

import com.neneth.oskar_game.models.Entities.MovieItemEntity;
import com.neneth.oskar_game.models.Entities.ResultEntity;
import com.neneth.oskar_game.models.MovieItem;
import com.neneth.oskar_game.models.Player;
import com.neneth.oskar_game.models.Room;
import com.neneth.oskar_game.repositories.MovieItemRepository;
import com.neneth.oskar_game.repositories.ResultRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ResultService {
    private final ResultRepository resultRepository;
    private final MovieItemRepository movieItemRepository;

    public ResultEntity saveFromRoom(Room room) {
        return resultRepository.save(new ResultEntity(
                room.getId(),
                room.getPlayers()
        ));
    }

    public Optional<ResultEntity> findById(String id) {
        return resultRepository.findById(id);
    }

    public ResultEntity findByIdMappedWithWonAsPlayerList(String id) {
        ResultEntity resultEntity = resultRepository.findById(id).orElse(null);
        if (resultEntity != null) {
            final List<Long> allMovieItemIdInResult = new ArrayList<>(new HashSet<>(resultEntity.getPlayers().stream().map(player ->
                    player.getMovieItems().stream().map(MovieItem::getMovieItemId).toList()
            ).flatMap(Collection::stream).toList()));
            final List<MovieItemEntity> allMovieItemsConcerned = this.movieItemRepository.findAllByMovieItemIdIn(
                    allMovieItemIdInResult
            );

            List<Player> players =  resultEntity.getPlayers().stream().map(player -> {
                List<MovieItemEntity> correctedMovieItems = player
                        .getMovieItems()
                        .stream()
                        .map(movieItem -> allMovieItemsConcerned.stream().filter(currentMovieItem -> currentMovieItem
                                .getMovieItemId().equals(movieItem.getMovieItemId()))
                                .findFirst()
                                .orElseThrow(() -> new IllegalStateException("Un Problème est survenue au moment de la récupération des nominées.")))
                        .toList();
                player.setMovieItems(correctedMovieItems.stream().map(correctedMovieItem -> new MovieItem().createFromEntity(correctedMovieItem)).toList());

                return player;
            }).toList();

            resultEntity.setPlayers(players);
            return resultEntity;
        }
        return null;
    }
}
