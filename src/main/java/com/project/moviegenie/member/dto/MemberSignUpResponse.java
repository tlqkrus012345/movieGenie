package com.project.moviegenie.member.dto;

import com.project.moviegenie.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignUpResponse {
    private String email;
    private String nickName;
    public static MemberSignUpResponse toDto(Member member) {
        return new MemberSignUpResponse(member.getEmail(), member.getNickName());
    }
}
