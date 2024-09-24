package org.ait.project.guideline.example.modules.permission.model.entity.key;

import jakarta.persistence.*;
import lombok.*;
import org.ait.project.guideline.example.modules.role.model.entity.Role;

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

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", updatable = false, insertable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Role role;

}
