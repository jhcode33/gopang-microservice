package com.gopang.oauth2server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class JoinDto {

    private Long id;
    private String username;
    private String password;

}
