package com.project.moviegenie.member.service;

import com.project.moviegenie.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SessionLoginService implements LoginService{

    private final HttpSession httpSession;
    private final MemberService memberService;
    public static final String MEMBER_ID = "member_id";

    @Override
    public void login(Long id) {
        httpSession.setAttribute(MEMBER_ID, id);
    }

    @Override
    public void logout() {
        httpSession.removeAttribute(MEMBER_ID);
    }

    @Override
    public Member getLoginMember() {
        Long memberId = (Long) httpSession.getAttribute(MEMBER_ID);

        return memberService.findMemberById(memberId);
    }

    @Override
    public Long getLoginMemberId() {
        return (Long) httpSession.getAttribute(MEMBER_ID);
    }
}
