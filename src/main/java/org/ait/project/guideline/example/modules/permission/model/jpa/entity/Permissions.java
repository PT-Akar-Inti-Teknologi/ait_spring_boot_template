package org.ait.project.guideline.example.modules.permission.model.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ait.project.guideline.example.modules.role.model.jpa.entity.Role;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "permissions", schema = "ait")
public class Permissions {
  @Id
  @GeneratedValue
  private String id;

  private String menu;

  @ManyToMany(mappedBy = "permissions")
  private List<Role> roleId;

  @CreatedDate
  private LocalDateTime createdAt;

}
