package com.project.moviegenie.member.dto;

import com.project.moviegenie.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginRequest {
    private String email;
    private String password;

    public static Member toEntity(MemberLoginRequest dto) {
        return Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }
}
