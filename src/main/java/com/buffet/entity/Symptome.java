package com.buffet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "symptomes")
@Data
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class Symptome {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "La description du symptôme est obligatoire")
    @Column(nullable = false)
    private String description;
    
    @NotNull(message = "La date d'apparition est obligatoire")
    @Column(nullable = false)
    private LocalDateTime dateApparition;
    
    @Enumerated(EnumType.STRING)
    private Severite severite;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repas_id", nullable = false)
    private Repas repas;
    
    public enum Severite {
        LEGER, MODERE, SEVERE
    }
}