package com.project.moviegenie.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);
    boolean existsByNickName(String nickName);
    Optional<Member> findMemberByEmail(String email);
    Optional<Member> findByProviderAndProviderId(String provider, String providerId);

}
