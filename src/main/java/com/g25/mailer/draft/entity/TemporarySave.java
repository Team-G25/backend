package com.g25.mailer.draft.entity;

import com.g25.mailer.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "temporary_saves", indexes = {
        @Index(name = "idx_temporary_saves_user_id", columnList = "user_id")
})
public class TemporarySave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "temporary_save_id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    /**
     * sender, receiver, reference(LIst?), title,
     */
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "saved_at", nullable = false)
    private LocalDate savedAt;

    @PrePersist
    protected void onCreate() {
        this.savedAt = LocalDateTime.now().toLocalDate();
    }
}
