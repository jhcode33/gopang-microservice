package chat.example.demo.security;

//import chat.example.demo.entity.User;
//import chat.example.demo.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserRepositoryUserDetailsService
//        implements UserDetailsService {
//
//  private UserRepository userRepo;
//
//  @Autowired
//  public UserRepositoryUserDetailsService(UserRepository userRepo) {
//    this.userRepo = userRepo;
//  }
//
//  @Override
//  public UserDetails loadUserByUsername(String username)
//      throws UsernameNotFoundException {
//    User user = userRepo.findByUserEmail(username);
//    if (user != null) {
//      System.out.println("-------------------------------");
//      System.out.println("username: " + user.getUserEmail());
//      System.out.println("password: " + user.getUserPassword());
//      return user;
//    }
//    throw new UsernameNotFoundException(
//                    "User '" + username + "' not found");
//  }
//
//}
