package com.gopang.oauth2server.service;

import com.gopang.oauth2server.dto.JoinDto;
import com.gopang.oauth2server.entity.Role;
import com.gopang.oauth2server.entity.User;
import com.gopang.oauth2server.repository.RoleRepository;
import com.gopang.oauth2server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserRepository userRepository;
    private final RoleRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User save(JoinDto joinDto){
        Role roleUser = repository.findByName("ROLE_USER").orElseThrow(() -> new IllegalArgumentException("no role"));


        return userRepository.save(User.builder()
                        .username(joinDto.getUsername())
                         .password(passwordEncoder.encode(joinDto.getPassword()))
                         .role(roleUser)
                         .active(Boolean.TRUE).build());

    }

}
