package com.project.moviegenie.member.service;

import com.project.moviegenie.member.domain.Member;

public interface LoginService {

    void login(Long id);
    void logout();
    Member getLoginMember();
    Long getLoginMemberId();
}
