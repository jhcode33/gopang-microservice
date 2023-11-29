//package chat.example.demo.dto;
//
//import chat.example.demo.entity.User;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//
//@Data
//@AllArgsConstructor
//public class FriendProfileDTO {
//    private Long id;
//    private String email;
//    private String name;
//    private String imgUrl;
//    private String blockedBy;
//    private String lastMsg;
//    private Date lastMsgAt;
//    private Date updatedAt;
//
//    public static FriendProfileDTO translate(User user) {
//        return new FriendProfileDTO(user.getUserId(), user.getUserEmail(), user.getUsername(), "", "", "lastMessage", new Date(), new Date());
//    }
//}
