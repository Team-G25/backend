package com.g25.mailer.user.entity;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원테이블 v2
 */
@Table(name = "users", indexes = {
        @Index(name = "email_idx", columnList = "email")
}) //이메일로 인덱스걸기
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    //email 컬럼으로  Index 설정
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    //다크모드/기본모드 설정 (추가)
    @Column(name = "theme", nullable = false)
    @Enumerated(EnumType.STRING)
    private Theme theme;


    @Builder
    public User(String email, String password, String auth) {
        this.email = email;
        this.password = password;
    }

    //return 권한
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    //계정만료 여부(레디스...)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //패스워드만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    //계정사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 테마 설정용 num
    public enum Theme {
        LIGHT,
        DARK
    }

}


