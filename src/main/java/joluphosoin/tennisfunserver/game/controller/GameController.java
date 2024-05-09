package joluphosoin.tennisfunserver.game.controller;

import joluphosoin.tennisfunserver.game.data.dto.GameCreationDto;
import joluphosoin.tennisfunserver.game.service.GameService;
import joluphosoin.tennisfunserver.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<ApiResponse> createGame(@Valid @RequestBody GameCreationDto gameDto) {
        try {
            gameService.createGame(gameDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponse(true, "GAME201", "Game created successfully", null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse(false, "INTERNAL_SERVER_ERROR", e.getMessage(), null)
            );
        }
    }
}
