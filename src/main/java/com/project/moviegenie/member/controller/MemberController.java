package com.project.moviegenie.member.controller;

import com.project.moviegenie.member.dto.MemberLoginRequest;
import com.project.moviegenie.member.dto.MemberLoginResponse;
import com.project.moviegenie.member.dto.MemberSignUpRequest;
import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.member.dto.MemberSignUpResponse;
import com.project.moviegenie.member.service.LoginService;
import com.project.moviegenie.member.service.MemberService;
import com.project.moviegenie.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/sign-up")
    public ApiResponse<MemberSignUpResponse> signUp(@RequestBody @Valid MemberSignUpRequest dto) {
        Member requestMember = MemberSignUpRequest.toEntity(dto, passwordEncoder);

        return ApiResponse.success(MemberSignUpResponse.toDto(memberService.signUp(requestMember)));
    }

    @PostMapping("login")
    public ApiResponse<MemberLoginResponse> login(@RequestBody @Valid MemberLoginRequest dto) {
        memberService.isValidMember(MemberLoginRequest.toEntity(dto), passwordEncoder);

        Member loginMember = memberService.findMemberByEmail(dto.getEmail());
        loginService.login(loginMember.getId());

        return ApiResponse.success(MemberLoginResponse.toDto(loginMember.getNickName()));
    }

    @GetMapping("logout")
    public ApiResponse<?> logout() {
        loginService.logout();

        return ApiResponse.success();
    }
}
