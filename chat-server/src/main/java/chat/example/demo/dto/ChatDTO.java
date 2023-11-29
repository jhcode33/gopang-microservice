//package chat.example.demo.dto;
//
//import chat.example.demo.entity.Chat;
//import chat.example.demo.entity.User;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.Date;
//
//@Data
//@AllArgsConstructor
//public class ChatDTO {
//
//    private long id;
//
//    private String senderId;
//
//    private String chatId;
//
//    private String content;
//
//    private String contentType;
//
//    private String files;
//
//    private Date createAt;
//
//    private Date updateAt;
//
//    private boolean read;
//
//    private User user;
//
//    public static ChatDTO translate(Chat chat){
//        return new ChatDTO(chat.getId(), chat.getSenderId(), chat.getChatId(), chat.getContent(),
//                chat.getContentType(), chat.getFiles(), chat.getCreateAt(), chat.getUpdateAt(), chat.isRead_(), chat.getUser());
//    }
//
//}