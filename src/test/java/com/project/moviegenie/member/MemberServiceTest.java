package com.project.moviegenie.member;

import com.project.moviegenie.exception.MovieGenieAppException;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

    @DisplayName("잘못된 이메일로 로그인하면 MovieGenieAppException 예외가 발생한다.")
    @Test
    void isValidMemberEmailTest() {
        Member member = Member.builder().email("wrongEmail@email.com").password("correctPassword").build();

        MovieGenieAppException ex = assertThrows(MovieGenieAppException.class,
                () -> memberService.isValidMember(member, passwordEncoder));

        assertEquals("이메일을 찾을 수 없습니다.", ex.getErrorMessage());
    }

    @DisplayName("잘못된 비밀번호로 로그인하면 NOT_FOUND_PASSWORD 예외가 발생한다.")
    @Test
    void isValidMemberPasswordTest() {
        Member member = Member.builder().email("test@email.com").password("correctPassword").build();
        Member loginMember = Member.builder().email("test@email.com").password("wrongPassword").build();

        when(memberRepository.findMemberByEmail(any())).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        MovieGenieAppException ex = assertThrows(MovieGenieAppException.class,
                () -> memberService.isValidMember(loginMember, passwordEncoder));

        assertEquals("패스워드를 찾을 수 없습니다.", ex.getErrorMessage());
    }
}
