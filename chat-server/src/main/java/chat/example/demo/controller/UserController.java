package chat.example.demo.controller;

//import chat.example.demo.dto.FriendProfileDTO;
//import chat.example.demo.dto.UserDTO;
//import chat.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

//    @Autowired
//    UserService userService;

//    @PostMapping("/a")
//    public void a(@RequestBody String a){
//        System.out.println(a);
//    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<String> singUp(@RequestBody UserDTO userDTO){
//        userService.register(userDTO);
//        return ResponseEntity.ok("ok");
//    }
//
////    @PostMapping("/chat")
//    public ResponseEntity<List<FriendProfileDTO>> chat(){
//        List<FriendProfileDTO> friendProfiles = userService.AllUser();
//
//        System.out.println("********************************************************");
//        System.out.println(friendProfiles);
//        return ResponseEntity.ok(friendProfiles);
//    }
//
//    @GetMapping("/user/me")
//    public ResponseEntity<FriendProfileDTO> me(@AuthenticationPrincipal UserDetails userDetails){
//        FriendProfileDTO meDTO = userService.getFriendProfile(userDetails.getUsername());
//        return ResponseEntity.ok(meDTO);
//    }
//
//    @GetMapping("/chat")
//    public ResponseEntity<FriendProfileDTO> email(@RequestParam(name="email") String email){
//
//        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//        System.out.println(email);
//        FriendProfileDTO friendProfileDTO = userService.getFriendProfile(email);
//        return ResponseEntity.ok(friendProfileDTO);
//    }
//
}
