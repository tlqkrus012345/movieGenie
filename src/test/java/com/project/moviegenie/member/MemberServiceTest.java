package com.project.moviegenie.member;

import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.member.domain.MemberRepository;
import com.project.moviegenie.member.dto.MemberSignUpRequest;
import com.project.moviegenie.member.service.GeneralMemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @InjectMocks
    private GeneralMemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @DisplayName("정상적으로 회원가입 요청을 하면 회원 레포가 호출된다.")
    @Test
    void signUp() {
        MemberSignUpRequest dto = new MemberSignUpRequest("test@email.com", "a1234567", "test");
        Member member = MemberSignUpRequest.toEntity(dto, passwordEncoder);

        memberService.signUp(member);

        verify(memberRepository, times(1)).save(member);
    }
}
