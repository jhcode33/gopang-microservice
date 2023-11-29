//package chat.example.demo.handler;
//
//import chat.example.demo.security.jwt.TokenProvider;
//import jakarta.security.auth.message.AuthException;
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.messaging.support.MessageHeaderAccessor;
//import org.springframework.stereotype.Component;
//
//import java.nio.file.AccessDeniedException;
//
//@RequiredArgsConstructor
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE + 99)
//public class StompHandler implements ChannelInterceptor {
//    private final TokenProvider tokenProvider;
//
//    @SneakyThrows
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//
//        System.out.println("preSend");
//        final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//
//        // websocket 연결시 헤더의 jwt token 유효성 검증
//        if (StompCommand.CONNECT == accessor.getCommand()) {
//            final String authorization = tokenProvider.extractJwt(accessor);
//
//            tokenProvider.validateToken(authorization);
//        }
//
//        return message;
//    }
//}