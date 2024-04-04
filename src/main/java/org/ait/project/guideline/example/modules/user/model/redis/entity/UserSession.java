package org.ait.project.guideline.example.modules.user.model.redis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.LocalDateTime;

@Getter
@Setter
@RedisHash("user_session")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserSession {
    @Id
    @GeneratedValue
    private Integer id;

    private LocalDateTime updatedAt;

    @TimeToLive
    private Long expirationInSeconds;
}
