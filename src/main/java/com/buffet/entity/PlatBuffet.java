package com.buffet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "plats_buffet")
@Data
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class PlatBuffet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom du plat est obligatoire")
    @Column(nullable = false)
    private String nomPlat;
    
    @Enumerated(EnumType.STRING)
    private CategorieAliment categorie;
    
    @PositiveOrZero(message = "La quantité par personne doit être positive ou nulle")
    private Integer quantiteParPersonne = 0;
    
    @PositiveOrZero(message = "Le coût unitaire doit être positif ou nul")
    @Column(precision = 8, scale = 2)
    private BigDecimal coutUnitaire = BigDecimal.ZERO;
    
    @ElementCollection
    @CollectionTable(name = "plat_allergenes", joinColumns = @JoinColumn(name = "plat_id"))
    @Column(name = "allergene")
    private List<String> allergenes;
    
    @ElementCollection
    @CollectionTable(name = "plat_ingredients", joinColumns = @JoinColumn(name = "plat_id"))
    @Column(name = "ingredient")
    private List<String> ingredients;
    
    @Column(columnDefinition = "TEXT")
    private String instructionsPreparation;
    
    @PositiveOrZero(message = "Le temps de préparation doit être positif ou nul")
    private Integer tempsPreparation = 0;
    
    @Column(columnDefinition = "INTEGER CHECK (difficulte >= 1 AND difficulte <= 5)")
    private Integer difficulte = 1;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;
    
    @LastModifiedDate
    private LocalDateTime dateModification;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buffet_id", nullable = false)
    private Buffet buffet;
    
    public enum CategorieAliment {
        ENTREE, PLAT_PRINCIPAL, DESSERT, BOISSON, ACCOMPAGNEMENT
    }
}