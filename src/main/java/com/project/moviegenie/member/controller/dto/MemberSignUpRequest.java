package com.project.moviegenie.member.controller.dto;

import com.project.moviegenie.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberSignUpRequest {
    private String email;
    private String password;
    private String nickName;
    public static Member toEntity(MemberSignUpRequest dto) {
        return Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .nickName(dto.getNickName())
                .build();
    }
}
