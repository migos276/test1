package com.buffet.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RepasDto {
    private Long id;
    
    @NotNull(message = "La date du repas est obligatoire")
    private LocalDateTime dateRepas;
    
    private List<String> alimentsConsommes;
    private String notes;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    
    private Long utilisateurId;
    private List<SymptomeDto> symptomes;
}