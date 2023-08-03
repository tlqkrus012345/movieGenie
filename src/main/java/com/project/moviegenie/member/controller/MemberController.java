package com.project.moviegenie.member.controller;

import com.project.moviegenie.member.controller.dto.MemberSignUpRequest;
import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @GetMapping("/sign-up")
    public void signUp(@RequestBody @Valid MemberSignUpRequest dto) {
        Member member = MemberSignUpRequest.toEntity(dto);
        memberService.signUp(member);
    }
}
