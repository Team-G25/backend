package com.g25.mailer.user.entity;

import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Table(name = "users", indexes = {
        @Index(name = "email_idx", columnList = "email")
}) //이메일로 인덱스걸기
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Hibernate 프록시 무시
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    // email 컬럼에 Index 설정
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    // 추가된 프로필 이미지 URL 필드
    @Setter
    @Getter
    @Column(name = "profile_image_url")
    private String profileImageUrl;


    // 추가된 테마 설정 필드 (기본값은 LIGHT)
    @Column(name = "theme", nullable = false)
    @Enumerated(EnumType.STRING)
    private Theme theme;

    @Builder
    public User(String email, String password, Theme theme) {
        this.email = email;
        this.password = password;

        this.theme = theme != null ? theme : Theme.LIGHT; //테마 설정
    }


    @Builder
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //임시저장에서 사용하는 메서드
    @Builder
    public User(String email){
        this.email = email;
    }


    //권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    //계정 만료 여부
    @Override
    public String getPassword() {
        return password;
    }

    // 계정 잠김 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //패스워드 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    //계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true;
    }


    // 테마 설정에 사용할 enum
    public enum Theme {
        LIGHT,
        DARK
    }

}


