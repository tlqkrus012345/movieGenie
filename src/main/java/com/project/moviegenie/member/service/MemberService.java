package com.project.moviegenie.member.service;

import com.project.moviegenie.member.domain.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public interface MemberService {
    Member signUp(Member member);
    Optional<Member> findMemberByEmail(String email);
    void isValidMember(Member member, PasswordEncoder passwordEncoder);
    boolean isDuplicatedEmail(String email);
    boolean isDuplicatedNickName(String nickName);
}
