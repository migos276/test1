package com.buffet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "repas")
@Data
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class Repas {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "La date du repas est obligatoire")
    @Column(nullable = false)
    private LocalDateTime dateRepas;
    
    @ElementCollection
    @CollectionTable(name = "repas_aliments", joinColumns = @JoinColumn(name = "repas_id"))
    @Column(name = "aliment")
    private List<String> alimentsConsommes;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;
    
    @LastModifiedDate
    private LocalDateTime dateModification;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;
    
    @OneToMany(mappedBy = "repas", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Symptome> symptomes;
}