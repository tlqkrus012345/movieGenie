package com.project.moviegenie.member.domain;

import lombok.Getter;

import javax.persistence.*;

@Table(name = "members")
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    private String password;
    private String nickName;

}
