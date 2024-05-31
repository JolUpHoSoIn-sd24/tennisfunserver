package joluphosoin.tennisfunserver.payment.controller;

import jakarta.servlet.http.HttpSession;
import joluphosoin.tennisfunserver.game.data.entity.Game;
import joluphosoin.tennisfunserver.game.repository.GameRepository;
import joluphosoin.tennisfunserver.payment.exception.PaymentServiceException;
import joluphosoin.tennisfunserver.payment.service.PaymentService;
import joluphosoin.tennisfunserver.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;
    private final GameRepository gameRepository;

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<ApiResponse> getPayment(HttpSession session) {
        String userId = (String) session.getAttribute("id");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResponse(false, "AUTH001", "User is not logged in", null)
            );
        }

        Collection<Game> games = gameRepository.findByPlayerIdsContaining(userId);
        if (games.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse(false, "GAME400", "No game information available for the user.", null)
            );
        }

        Game selectedGame = games.iterator().next();

        try {
            Map<String, Object> paymentInfo = paymentService.getPaymentInfo(selectedGame);
            return ResponseEntity.ok(new ApiResponse(true, "PAY200", "Payment information retrieved successfully.", paymentInfo));
        } catch (PaymentServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse(false, "INTERNAL_SERVER_ERROR", e.getMessage(), null)
            );
        }
    }
}
