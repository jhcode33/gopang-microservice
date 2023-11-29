//package chat.example.demo.interceptor;
//
//import chat.example.demo.security.jwt.TokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.http.server.ServletServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//
//import java.util.Map;
//
//@Component
//@RequiredArgsConstructor
//public class StompHandShakeInterceptor implements HandshakeInterceptor {
//    @Autowired
//    private final TokenProvider tokenProvider;
//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
////        String token = tokenProvider.createToken(); // 토큰 생성 또는 가져오기
////        System.out.println("////////////////////////////////////////////");
////        if (request instanceof ServletServerHttpRequest) {
////            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
////            servletRequest.getHeaders().add("Authorization", "Bearer " + token);
////        }
//        return true;
//    }
//
//    @Override
//    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                               WebSocketHandler wsHandler, Exception exception) {
//        // WebSocket 연결 후 처리 로직
//    }
//}
