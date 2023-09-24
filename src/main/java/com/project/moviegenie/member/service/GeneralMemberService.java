package com.project.moviegenie.member.service;

import com.project.moviegenie.exception.ErrorCode;
import com.project.moviegenie.exception.MovieGenieAppException;
import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email);
    }

    @Override
    public Member findMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new MovieGenieAppException(ErrorCode.NOT_FOUND_MEMBER));
    }

    @Override
    public void isValidMember(Member loginMember, PasswordEncoder passwordEncoder) {
        Member findMember = memberRepository.findMemberByEmail(loginMember.getEmail())
                .orElseThrow(() -> new MovieGenieAppException(ErrorCode.NOT_FOUND_EMAIL));

        if (!passwordEncoder.matches(loginMember.getPassword(), findMember.getPassword())) {
            throw new MovieGenieAppException(ErrorCode.NOT_FOUND_PASSWORD);
        }
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
