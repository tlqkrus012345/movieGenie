package com.project.moviegenie.member.dto;

import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.member.domain.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignUpRequest {

    @NotBlank
    @Email( message = "유효하지 않은 이메일입니다.",
            regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    @NotBlank
    @Pattern( message = "8글자 이상 16글자 이하 최소 한개 이상의 영문과 숫자를 포함하여 작성해주세요.",
              regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,16}$")
    private String password;

    @NotBlank
    @Pattern( message = "특수문자와 공백을 허용하지 않으며 2글자 이상 16글자 이하로 작성해주세요.",
              regexp = "^[a-zA-Z\\d가-힣]{2,16}$")
    private String nickName;
    public static Member toEntity(MemberSignUpRequest dto, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickName(dto.getNickName())
                .memberRole(MemberRole.MEMBER)
                .build();
    }
}
