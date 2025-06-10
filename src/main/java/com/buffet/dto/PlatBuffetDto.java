package com.buffet.dto;

import com.buffet.entity.PlatBuffet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PlatBuffetDto {
    private Long id;
    
    @NotBlank(message = "Le nom du plat est obligatoire")
    private String nomPlat;
    
    private PlatBuffet.CategorieAliment categorie;
    
    @PositiveOrZero(message = "La quantité par personne doit être positive ou nulle")
    private Integer quantiteParPersonne;
    
    @PositiveOrZero(message = "Le coût unitaire doit être positif ou nul")
    private BigDecimal coutUnitaire;
    
    private List<String> allergenes;
    private List<String> ingredients;
    private String instructionsPreparation;
    
    @PositiveOrZero(message = "Le temps de préparation doit être positif ou nul")
    private Integer tempsPreparation;
    
    private Integer difficulte;
    private String notes;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    
    private Long buffetId;
    
    // Données calculées
    private BigDecimal coutTotal;
}