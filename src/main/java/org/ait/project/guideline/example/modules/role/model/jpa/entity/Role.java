package org.ait.project.guideline.example.modules.role.model.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.ait.project.guideline.example.modules.permission.model.jpa.entity.Permissions;
import org.ait.project.guideline.example.modules.user.model.jpa.entity.User;
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
@Table(name = "role", schema = "ait")
public class Role {
    @Id
    @GeneratedValue
    private String id;

    private String name;
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
      name = "role_permissions", schema = "ait", 
      joinColumns = {@JoinColumn(name = "role_id")},
      inverseJoinColumns = {@JoinColumn(name = "permissions_id")})
    private List<Permissions> permissions;
    
    //@OneToOne(mappedBy = "roleId")
    //private User user;
    @ManyToMany(mappedBy = "roleId")
    private List<User> user;

    @CreatedDate
    private LocalDateTime createdAt;

}
