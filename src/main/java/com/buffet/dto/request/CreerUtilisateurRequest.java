package com.buffet.dto.request;

import com.buffet.dto.UtilisateurDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreerUtilisateurRequest {
    
    @Valid
    private UtilisateurDto utilisateur;
    
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String motDePasse;
}