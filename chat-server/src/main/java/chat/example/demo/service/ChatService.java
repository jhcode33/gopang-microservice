//package chat.example.demo.service;
//
//import chat.example.demo.dto.ChatDTO;
//import chat.example.demo.repository.ChatRepository;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@Service
//public class ChatService {
//    @Autowired
//    ChatRepository chatRepository;
//
//    @Transactional
//    public List<ChatDTO> messages(List<Long> key){
//        return key.stream()
//                .map(id -> chatRepository.findById(id))
//                .flatMap(optional -> optional.map(Stream::of).orElseGet(Stream::empty))
//                .map(ChatDTO::translate)
//                .collect(Collectors.toList());
//    }
//}
