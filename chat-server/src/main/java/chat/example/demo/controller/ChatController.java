package chat.example.demo.controller;

//import chat.example.demo.service.ChatService;
import chat.example.demo.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ChatController {

    @Autowired
    private final SimpMessagingTemplate template;

//    @Autowired
//    private final ChatService chatService;

//    @MessageMapping("/chatroom/{id}") // 실제론 메세지 매핑으로 pub/chatroom/{id} 임
//    public void sendMessage(@DestinationVariable("id") Long id, String message) {
//        System.out.println(message);
//        // /sub/chatroom/{id}로 메세지 보냄
//        template.convertAndSend("/sub/chatroom/" + id, message);
//    }

    @MessageMapping("/chatroom") // 실제론 메세지 매핑으로 pub/chatroom/{id} 임
    public void sendMessage(ChatMessage message) {
        System.out.println(message);
        // /sub/chatroom/{id}로 메세지 보냄
        template.convertAndSend("/sub/chatroom", message);
    }

//    @PostMapping("/chat/messages")
//    public ResponseEntity<List<ChatDTO>> messages(@RequestBody List<Long> keys){
//        return ResponseEntity.ok(chatService.messages(keys));
//    }
}
