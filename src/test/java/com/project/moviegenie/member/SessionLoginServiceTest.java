package com.project.moviegenie.member;

import com.project.moviegenie.member.service.SessionLoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;

import static com.project.moviegenie.member.service.SessionLoginService.MEMBER_ID;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SessionLoginServiceTest {

    private SessionLoginService loginService;

    private MockHttpSession httpSession;

    @BeforeEach
    void setUp() {
        httpSession = new MockHttpSession();
        loginService = new SessionLoginService(httpSession);
    }

    @DisplayName("세션 로그인 성공 테스트")
    @Test
    void login() {
        loginService.login(1L);

        assertThat(httpSession.getAttribute(MEMBER_ID)).isNotNull();
        assertThat(httpSession.getAttribute(MEMBER_ID)).isEqualTo(1L);
    }

    @DisplayName("세션 로그아웃 성공 테스트")
    @Test
    void logout() {
        httpSession.setAttribute(MEMBER_ID, 1L);

        loginService.logout();

        assertThat(httpSession.getAttribute(MEMBER_ID)).isNull();
    }
}
