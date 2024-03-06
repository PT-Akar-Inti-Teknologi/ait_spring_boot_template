package org.ait.project.guideline.example.modules.product.model.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "product", schema = "ait")
public class Product {
    @Id
    @GeneratedValue
    private String id;

    private String name;

    private Integer quantity;
    
    @CreatedDate
    private LocalDateTime createdAt;

}
