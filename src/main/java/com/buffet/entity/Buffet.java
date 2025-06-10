package com.buffet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "buffets")
@Data
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class Buffet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom de l'événement est obligatoire")
    @Column(nullable = false)
    private String nomEvenement;
    
    @NotNull(message = "La date de l'événement est obligatoire")
    @Column(nullable = false)
    private LocalDateTime dateEvenement;
    
    @Positive(message = "Le nombre d'invités doit être positif")
    @Column(nullable = false)
    private Integer nombreInvites;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal budgetTotal;
    
    @Enumerated(EnumType.STRING)
    private TypeEvenement typeEvenement;
    
    @Enumerated(EnumType.STRING)
    private StatutBuffet statut = StatutBuffet.PLANIFIE;
    
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
    
    @OneToMany(mappedBy = "buffet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlatBuffet> plats;
    
    public enum TypeEvenement {
        ANNIVERSAIRE, MARIAGE, ENTREPRISE, FAMILIAL, AUTRE
    }
    
    public enum StatutBuffet {
        PLANIFIE, EN_PREPARATION, TERMINE, ANNULE
    }
}