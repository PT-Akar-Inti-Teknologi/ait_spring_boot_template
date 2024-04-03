package org.ait.project.guideline.example.utils.specification.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


import jakarta.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "search", schema = "ait")
public class Search{


	@Id
    @GeneratedValue
    private String id;

    private String className;

    private String searchField;
    
    @CreatedDate
    private LocalDateTime createdAt;

}
