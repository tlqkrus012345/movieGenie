package com.project.moviegenie.member.controller;

import com.project.moviegenie.member.dto.MemberSignUpRequest;
import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.member.dto.MemberSignUpResponse;
import com.project.moviegenie.member.service.MemberService;
import com.project.moviegenie.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/sign-up")
    public Response<MemberSignUpResponse> signUp(@RequestBody @Valid MemberSignUpRequest dto) {
        //회원 이메일, 닉네임 중복여부 확인
        //return Response.error();
        Member requestMember = MemberSignUpRequest.toEntity(dto, passwordEncoder);
        Member responseMember = memberService.signUp(requestMember);

        return Response.success(MemberSignUpResponse.toDto(responseMember));
    }
}
