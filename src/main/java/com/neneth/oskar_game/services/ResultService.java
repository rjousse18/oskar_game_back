package com.neneth.oskar_game.services;

import com.neneth.oskar_game.models.Entities.ResultEntity;
import com.neneth.oskar_game.models.Player;
import com.neneth.oskar_game.models.Room;
import com.neneth.oskar_game.repositories.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    public ResultEntity saveFromRoom(final Room room) {
        return resultRepository.save(new ResultEntity(
                room.getId(),
                room.getPlayers().stream().map(Player::toMinimizedPlayer).toList(),
                new ArrayList<>()
        ));
    }

    public ResultEntity findByIdMappedWithWonAsPlayerList(String id) {
        ResultEntity resultEntity = resultRepository.findById(id).orElse(null);
        if (resultEntity != null) {
            final List<ResultEntity.PredictionResult> predictionResults = this.categoryRepository.findAllWithWinner();
            resultEntity.setPredictionResults(predictionResults);

            resultRepository.save(resultEntity); // Just in case, not useful for now but maybe later.
        }
        return resultEntity;
    }
}
