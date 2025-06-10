package com.buffet.dto;

import com.buffet.entity.PlatBuffet;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AlimentDto {
    private Long id;
    
    @NotBlank(message = "Le nom de l'aliment est obligatoire")
    private String nom;
    
    private PlatBuffet.CategorieAliment categorie;
    private List<String> allergenesCommuns;
    private String description;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
}