package com.project.moviegenie.member.controller;

import com.project.moviegenie.member.dto.MemberSignUpRequest;
import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.member.service.MemberService;
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
    public void signUp(@RequestBody @Valid MemberSignUpRequest dto) {
        Member member = MemberSignUpRequest.toEntity(dto, passwordEncoder);
        memberService.signUp(member);
    }
}
