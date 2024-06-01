package joluphosoin.tennisfunserver.websocket.service;

import joluphosoin.tennisfunserver.game.data.dto.GameDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendMatchNotification(String userId, GameDetailsDto gameDetailsDto) {
        messagingTemplate.convertAndSend("/game/create/"+userId , gameDetailsDto);
    }
    public void sendGameNotification(String userId, GameDetailsDto gameDetailsDto) {
        messagingTemplate.convertAndSend("/game/paid/"+userId , gameDetailsDto);
    }
}
