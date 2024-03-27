package org.ait.project.guideline.example.modules.user.model.jpa.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.ait.project.guideline.example.modules.role.model.jpa.entity.Role;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user", schema = "ait")
public class User{


	@Id
    @GeneratedValue
    private String id;

    private String username;

    private String email;
    
    private String phoneNumber;
    
    private String password;
    
    private String address;
    
    private String firstName;
    
    private String lastName;
    
    /*@OneToOne(cascade = CascadeType.ALL)
    	@JoinColumn(name="role_id"
    	,referencedColumnName="id")
    private Role roleId;
*/
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
      name = "users_roles",  schema = "ait", 
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roleId;
    
    @CreatedDate
    private LocalDateTime createdAt;

}
