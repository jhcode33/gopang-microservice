package chat.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * POJO representing the chat message
 */

@Data
@AllArgsConstructor
public class ChatMessage {
    private String text;
    private String username;
    private String avatar;
}
