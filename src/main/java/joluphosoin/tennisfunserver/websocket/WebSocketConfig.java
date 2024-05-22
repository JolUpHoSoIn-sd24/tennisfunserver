package joluphosoin.tennisfunserver.websocket;

import joluphosoin.tennisfunserver.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final UserRepository userRepository; // UserRepository 필드 추가

    // UserRepository를 생성자 주입
    public WebSocketConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //웹 소켓 endpoint 등록
        //origin은 편의상 모두 허용
        registry.addHandler(webSocketHandler(), "/websocket")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        //websocket handler를 빈으로 생성하여 등록한다.
        return new WebSocketHandler(userRepository);
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        //Text Message의 최대 버퍼 크기 설정
        container.setMaxTextMessageBufferSize(8192);
        //Binary Message의 최대 버퍼 크기 설정
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }
}