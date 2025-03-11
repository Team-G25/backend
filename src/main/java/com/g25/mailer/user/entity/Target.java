package com.g25.mailer.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "targets")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Target {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String targetName;  //직장인, 학생
}
