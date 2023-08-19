package com.project.moviegenie.oauth;

import com.project.moviegenie.config.auth.PrincipalDetails;
import com.project.moviegenie.member.domain.Member;
import com.project.moviegenie.member.domain.MemberRepository;
import com.project.moviegenie.member.domain.MemberRole;
import com.project.moviegenie.member.service.LoginService;
import com.project.moviegenie.oauth.provider.GoogleUserInfo;
import com.project.moviegenie.oauth.provider.NaverUserInfo;
import com.project.moviegenie.oauth.provider.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final LoginService loginService;
    private final MemberRepository memberRepository;
    /**
     구글 로그인을 통해 인증된 사용자에 대한 정보를 처리하는 함수
     super.loadUser는 OAuth 클라이언트 라이브러리가 구글 로그인 버튼을 클릭하여 로그인이 완료되면 받은 code를 기반으로 accessToken을 요청하고,
     이를 통해 사용자 정보를 가져오는 메서드입니다.
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("userRequest clientRegistration: " + userRequest.getClientRegistration());
        System.out.println("oAuthUser: " + oAuth2User);

        return processOAuth2User(userRequest, oAuth2User);
    }
    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = null;

        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        }

        Optional<Member> memberOptional =
                memberRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());

        Member member;

        if (memberOptional.isPresent()) {
            member = memberOptional.get();

        } else {
            member = Member.builder()
                       .email(oAuth2UserInfo.getEmail())
                       .password(oAuth2UserInfo.getNickName()+oAuth2UserInfo.getProvider()+1)
                       .nickName(oAuth2UserInfo.getNickName())
                       .memberRole(MemberRole.MEMBER)
                       .provider(oAuth2UserInfo.getProvider())
                       .providerId(oAuth2UserInfo.getProviderId())
                       .build();
            memberRepository.save(member);
        }
        loginService.login(member.getId());

        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }
}
