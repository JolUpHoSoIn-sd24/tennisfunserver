package joluphosoin.tennisfunserver.game.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import joluphosoin.tennisfunserver.game.data.dto.GameCreationDto;
import joluphosoin.tennisfunserver.game.data.dto.GameDetailsDto;
import joluphosoin.tennisfunserver.game.data.entity.Game;
import joluphosoin.tennisfunserver.game.exception.CreateGameException;
import joluphosoin.tennisfunserver.game.exception.GetGameException;
import joluphosoin.tennisfunserver.game.repository.GameRepository;
import joluphosoin.tennisfunserver.game.service.GameService;
import joluphosoin.tennisfunserver.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/game", produces = "application/json; charset=utf-8")
public class GameController {

    private final GameService gameService;
    private final GameRepository gameRepository;

    @PostMapping(value = "")
    public ResponseEntity<ApiResponse> createGame(@Valid @RequestBody GameCreationDto gameDto) {
        try {
            gameService.createGame(gameDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponse(true, "GAME201", "Game created successfully", null)
            );
        } catch (CreateGameException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ApiResponse(false, "GAME409", e.getMessage(), null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse(false, "INTERNAL_SERVER_ERROR", e.getMessage(), null)
            );
        }
    }

    @DeleteMapping(value = "")
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

    @GetMapping(value = "")
    public ResponseEntity<ApiResponse> getGameDetails(HttpSession session) {
        String userId = (String) session.getAttribute("id");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResponse(false, "AUTH001", "User is not logged in", null)
            );
        }

        try {
            Game game = gameService.findGameByUserId(userId);
            if (game == null) {
                return ResponseEntity.noContent().build();
            }
            GameDetailsDto gameDetails = gameService.transformGameToDto(game);
            Map<String, Object> result = new HashMap<>();
            result.put("gameId", gameDetails.getGameId());
            result.put("state", gameDetails.getState());
            result.put("players", gameDetails.getPlayers());
            result.put("court", gameDetails.getCourt());
            result.put("startTime", gameDetails.getStartTime());
            result.put("endTime", gameDetails.getEndTime());
            result.put("rentalCost", gameDetails.getRentalCost());

            if(gameDetails.getChatRoomId()!=null){
                result.put("chatRoomId", gameDetails.getChatRoomId());
            }
            return ResponseEntity.ok(new ApiResponse(true, "GAME200", "Game details retrieved successfully", result));
        } catch (GetGameException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse(false, "GAME404", "Game not found", null)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse(false, "INTERNAL_SERVER_ERROR", e.getMessage(), null)
            );
        }
    }
//
//    @GetMapping("/history")
//    public ApiResult<?> getGameHistory(@SessionAttribute("id") String userId){
//        return ApiResult.onSuccess(gameService.getGameHistory(userId));
//    }
}
