package com.project.moviegenie.member.service;

import com.project.moviegenie.member.domain.Member;

public interface MemberService {
    Member signUp(Member member);
    boolean isDuplicatedEmail(String email);
    boolean isDuplicatedNickName(String nickName);
}
