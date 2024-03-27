package org.ait.project.guideline.example.modules.permission.model.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.ait.project.guideline.example.modules.role.model.jpa.entity.Role;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

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
