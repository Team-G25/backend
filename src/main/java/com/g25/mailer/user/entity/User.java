package com.g25.mailer.user.entity;

import java.util.Collection;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Data
@Table(name = "users", indexes = { @Index(name = "email_idx", columnList = "email") }) //이메일로 인덱스걸기
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Hibernate 프록시 무시
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    //유저이름
    @Column(name = "nickname", nullable = false)
    private String nickname = "유저";

    // email 컬럼에 Index 설정
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    //프로필 이미지 URL(s3)
    @Column(name = "profile_image_url", nullable = true)
    private String profileImageUrl;

    @Column(name = "theme", nullable = false)
    @Enumerated(EnumType.STRING)
    private Theme theme = Theme.LIGHT;


    //spring sercurity 관련
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    //UserDetail때문에 선언해야함.
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }


    //다크, 기본 모드 설정
    public enum Theme {
        LIGHT,
        DARK
    }

    @Override
    public boolean isEnabled() {
        return true; // 또는 계정 활성 상태에 따라 로직 정의
    }

}


