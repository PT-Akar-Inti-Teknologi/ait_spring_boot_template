package org.ait.project.guideline.example.modules.permission.model.entity.key;

import lombok.*;
import org.ait.project.guideline.example.modules.role.model.entity.Role;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class PermissionId implements Serializable {
    private static final long serialVersionUID = -2363081794972428850L;

    private Integer menuId;
    @JoinColumn(name = "role_id")
    @ManyToOne
    private Role role;

}
