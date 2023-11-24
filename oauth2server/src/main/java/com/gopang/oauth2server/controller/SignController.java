package com.gopang.oauth2server.controller;

import com.gopang.oauth2server.dto.JoinDto;
import com.gopang.oauth2server.entity.User;
import com.gopang.oauth2server.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignController {

    private final JoinService joinService;

    @PostMapping("join")
    public User join(@RequestBody JoinDto joinDto){
        return joinService.save(joinDto);
    }

    @PostMapping("/login")
    public String check() {
        return "good test";
    }
}
