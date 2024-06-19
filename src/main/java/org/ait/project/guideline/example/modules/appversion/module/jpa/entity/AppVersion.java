package org.ait.project.guideline.example.modules.appversion.module.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigInteger;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ait.project.guideline.example.shared.constant.enums.TypeAppVersion;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "app_version", schema = "ait")
public class AppVersion {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "app_version_gen")
  @SequenceGenerator(name = "app_version_gen", sequenceName = "app_version_seq", allocationSize = 1)
  private BigInteger id;

  private String version;

  private String platform;

  @Enumerated(EnumType.STRING)
  private TypeAppVersion type;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Override
  public String toString() {
    return "AppVersion{" +
        "id=" + id +
        ", version='" + version + '\'' +
        ", platform='" + platform + '\'' +
        ", type=" + type +
        ", createdAt=" + createdAt +
        ", updatedAt=" + updatedAt +
        '}';
  }
}
