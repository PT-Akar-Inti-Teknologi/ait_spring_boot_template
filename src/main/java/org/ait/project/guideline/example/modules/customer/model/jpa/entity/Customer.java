package org.ait.project.guideline.example.modules.customer.model.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.ait.project.guideline.example.modules.order.model.jpa.entity.Order;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "customer", schema = "ait")
public class Customer {
    @Id
    @GeneratedValue
    private String id;

    @OneToOne(mappedBy = "customerId", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Order order;
    
    private String status;

    @CreatedDate
    private LocalDateTime createdAt;

}
