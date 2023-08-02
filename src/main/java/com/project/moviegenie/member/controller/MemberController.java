package com.project.moviegenie.member.controller;

import com.project.moviegenie.member.controller.dto.MemberSignUpRequest;
import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    public void signUp(MemberSignUpRequest dto) {
        Member member = MemberSignUpRequest.toEntity(dto);
        memberService.signUp(member);
    }
}
