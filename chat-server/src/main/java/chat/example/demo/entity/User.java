package chat.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

//@Data
//@Entity
//@Table(name = "user")
//@NoArgsConstructor(access =  AccessLevel.PUBLIC)
//@AllArgsConstructor
//public class User implements UserDetails {
//
//    //private static final long serialVersionUID = 1L;
//
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "user_id")
//    private long userId;
//
//    @Column
//    private String userEmail;
//
//    @Column
//    private String userPassword;
//
//    @Column
//    private String userName;
//
//    @Column
//    private String userPhoneNumber;
//
//    @Column
//    private String userZipCode;
//
//    @Column
//    private String userAddress;
//
//    @Column
//    private String userDetailAddress;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
//    }
//    @Override
//    public String getPassword() {
//        return userPassword;
//    }
//
//    @Override
//    public String getUsername() {
//        return userEmail;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
