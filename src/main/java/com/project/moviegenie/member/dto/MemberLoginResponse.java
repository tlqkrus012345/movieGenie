package com.project.moviegenie.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginResponse {
    private String nickName;

    public static MemberLoginResponse toDto(String email) {
        return new MemberLoginResponse(email);
    }
}
