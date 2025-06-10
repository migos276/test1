package com.buffet.dto;

import com.buffet.entity.Buffet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BuffetDto {
    private Long id;
    
    @NotBlank(message = "Le nom de l'événement est obligatoire")
    private String nomEvenement;
    
    @NotNull(message = "La date de l'événement est obligatoire")
    private LocalDateTime dateEvenement;
    
    @Positive(message = "Le nombre d'invités doit être positif")
    private Integer nombreInvites;
    
    private BigDecimal budgetTotal;
    private Buffet.TypeEvenement typeEvenement;
    private Buffet.StatutBuffet statut;
    private String notes;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    
    private Long utilisateurId;
    private List<PlatBuffetDto> plats;
    
    // Données calculées
    private Integer nombrePlats;
    private BigDecimal coutEstime;
    private Integer tempsPreparationTotal;
    private BigDecimal coutParPersonne;
    private Boolean respectBudget;
}