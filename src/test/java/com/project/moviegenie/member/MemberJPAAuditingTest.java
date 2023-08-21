package com.project.moviegenie.member;

import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.member.domain.MemberRepository;
import com.project.moviegenie.member.domain.MemberRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class MemberJPAAuditingTest {

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("회원가입시 생성시간이 저장된다")
    @Test
    public void BaseTimeEntity() {
        LocalDateTime now = LocalDateTime.of(2023, 8, 21, 0, 0);
        memberRepository.save(Member.builder()
                .email("test@email.com")
                .password("a1234567")
                .nickName("test")
                .memberRole(MemberRole.MEMBER)
                .build());

        List<Member> membersList = memberRepository.findAll();

        Member member = membersList.get(0);

        System.out.println("======================");
        System.out.println("createDate : " + member.getCreatedDate());
        System.out.println("modifiedDate : " + member.getModifiedDate());

        Assertions.assertThat(member.getCreatedDate().isAfter(now));
        Assertions.assertThat(member.getModifiedDate().isAfter(now));
    }
}
