package com.project.moviegenie.member.service;

import com.project.moviegenie.exception.ErrorCode;
import com.project.moviegenie.exception.MovieGenieAppException;
import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeneralMemberService implements MemberService{

    private final MemberRepository memberRepository;
    @Override
    public Member signUp(Member member) {

        if (isDuplicatedEmail(member.getEmail())) {
            throw new MovieGenieAppException(ErrorCode.DUPLICATED_EMAIL);
        }

        if (isDuplicatedNickName(member.getNickName())) {
            throw new MovieGenieAppException(ErrorCode.DUPLICATED_NICKNAME);
        }

        return memberRepository.save(member);
    }

    @Override
    public boolean isDuplicatedEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public boolean isDuplicatedNickName(String nickName) {
        return memberRepository.existsByNickName(nickName);
    }

}
