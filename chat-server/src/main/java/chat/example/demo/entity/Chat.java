package chat.example.demo.entity;

//
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Date;
//
//@Data
//@Entity
//@Table(name = "chat")
//@NoArgsConstructor(access = AccessLevel.PUBLIC)
//@AllArgsConstructor
//public class Chat {
//
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column
//    private long id;
//
//    @Column
//    private String senderId;
//
//    @Column
//    private String chatId;
//
//    @Column
//    private String content;
//
//    @Column
//    private String contentType;
//
//    @Column
//    private String files;
//
//    @Column
//    private Date createAt;
//
//    @Column
//    private Date updateAt;
//
//    @Column
//    private boolean read_;
//
//    @ManyToOne
//    @JoinColumn(name= "user_id")
//    private User user;
//}
