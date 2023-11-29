//package chat.example.demo.service;
//
//import chat.example.demo.dto.FriendProfileDTO;
//import chat.example.demo.dto.UserDTO;
//import chat.example.demo.entity.User;
//import chat.example.demo.repository.UserRepository;
//import chat.example.demo.security.RegistrationForm;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class UserService {
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Transactional
//    public void register(UserDTO userDTO){
//        User user = RegistrationForm.toUser(passwordEncoder, userDTO);
//        userRepository.save(user);
//    }
//
//    @Transactional
//    public List<FriendProfileDTO> AllUser() {
//        return userRepository
//                    .findAll()
//                    .stream()
//                    .map(FriendProfileDTO::translate)
//                    .toList();
//    }
//
//    @Transactional
//    public FriendProfileDTO getFriendProfile(String userEmail){
//        return FriendProfileDTO.translate(userRepository.findByUserEmail(userEmail));
//    }
//
//
//}
