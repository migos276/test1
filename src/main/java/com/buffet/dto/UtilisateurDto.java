package com.buffet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UtilisateurDto {
    private Long id;
    
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    
    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;
    
    @Email(message = "Format d'email invalide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;
    
    private String telephone;
    private String allergiesConnues;
    private String preferencesAlimentaires;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    
    // Pour les réponses avec données liées
    private List<BuffetDto> buffets;
    private List<RepasDto> repas;
}