package chat.example.demo.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SignInResponse {
    private String accessToken;
    private String tokenType;
    private String name;
    private String email;
    private String imageUrl;
    private Long id;
}
