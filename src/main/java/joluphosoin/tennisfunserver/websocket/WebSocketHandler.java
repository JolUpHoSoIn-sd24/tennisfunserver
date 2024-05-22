package joluphosoin.tennisfunserver.websocket;

import joluphosoin.tennisfunserver.payload.code.status.ErrorStatus;
import joluphosoin.tennisfunserver.payload.exception.GeneralException;
import joluphosoin.tennisfunserver.user.data.entity.User;
import joluphosoin.tennisfunserver.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
    private final UserRepository userRepository;

    public WebSocketHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //websocket handshake가 완료되어 연결이 수립될 때 호출
    @Override
    @Transactional
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = (String) session.getAttributes().get("id");

        log.info("connection established, session id={}", session.getId());
        sessionMap.putIfAbsent(session.getId(), session);

        User user = userRepository.findById(userId).orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        user.setWebSocketId(session.getId());

        userRepository.save(user);

    }

    //websocket 오류가 발생했을 때 호출
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("session transport exception, session id={}, error={}", session.getId(), exception.getMessage());
        sessionMap.remove(session.getId());
    }

    //websocket 세션 연결이 종료되었을 때 호출
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("connection closed, sesson id={}, close status={}", session.getId(), status);
        sessionMap.remove(session.getId());
    }

    //websocket session 으로 메시지가 수신되었을 때 호출
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //getPayload를 통해 websocket 메시지 payload를 가져온다.
        String payload = message.getPayload();
        log.info("received message, session id={}, message={}", session.getId(), payload);
        //broadcasting message to all session
        sessionMap.forEach((sessionId, session1) -> {
            try {
                session1.sendMessage(message);
            } catch (IOException e) {
                log.error("fail to send message to session id={}, error={}",
                        sessionId, e.getMessage());
            }
        });
    }

    // 알림을 보내는 메서드
    public void sendNotification(String sessionId, String notification) {
        WebSocketSession session = sessionMap.get(sessionId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(notification));
                log.info("Notification sent to session id={}", sessionId);
            } catch (IOException e) {
                log.error("Fail to send notification to session id={}, error={}", sessionId, e.getMessage());
            }
        }
    }
}

