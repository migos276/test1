package com.buffet.dto;

import com.buffet.entity.Symptome;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SymptomeDto {
    private Long id;
    
    @NotBlank(message = "La description du symptôme est obligatoire")
    private String description;
    
    @NotNull(message = "La date d'apparition est obligatoire")
    private LocalDateTime dateApparition;
    
    private Symptome.Severite severite;
    private String notes;
    private LocalDateTime dateCreation;
    
    private Long repasId;
}