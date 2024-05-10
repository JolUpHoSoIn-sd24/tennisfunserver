package joluphosoin.tennisfunserver.game.controller;

import jakarta.servlet.http.HttpSession;
import joluphosoin.tennisfunserver.game.data.dto.GameCreationDto;
import joluphosoin.tennisfunserver.game.repository.GameRepository;
import joluphosoin.tennisfunserver.game.service.GameService;
import joluphosoin.tennisfunserver.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;
    private final GameRepository gameRepository;

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

    @DeleteMapping(value = "", produces = "application/json")
    public ResponseEntity<ApiResponse> deleteGames(HttpSession session) {
        String userId = (String) session.getAttribute("id");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResponse(false, "AUTH001", "User is not logged in", null)
            );
        }

        try {
            gameRepository.deleteByPlayerIdsContaining(userId);
            return ResponseEntity.ok(new ApiResponse(true, "GAMES_DELETED", "All games involving the user have been successfully deleted.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse(false, "INTERNAL_SERVER_ERROR", "Failed to delete games.", null)
            );
        }
    }
}
