package com.neneth.oskar_game.controllers;

import com.neneth.oskar_game.models.Entities.ResultEntity;
import com.neneth.oskar_game.services.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultRestController {
    private final ResultService resultService;

    @GetMapping("/{id}")
    public ResponseEntity<ResultEntity> getResultById(@PathVariable String id) {

        final ResultEntity finalResult = resultService.findByIdMappedWithWonAsPlayerList(id);

        if (finalResult == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(finalResult);
    }

    @PatchMapping("/{id}/won")
    public ResponseEntity<Boolean> setMovieItemAsWon(@PathVariable Long id) {
        final boolean success = resultService.setMovieItemAsWon(id);

        if (!success) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(true);
    }
}
