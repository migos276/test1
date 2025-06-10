package com.buffet.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PlanningPreparationResponse {
    private String buffetNom;
    private LocalDateTime dateEvenement;
    private List<TachePreparation> planningPreparation;
    private Integer tempsPreparationTotal;
    private List<String> recommandations;
    
    @Data
    public static class TachePreparation {
        private String plat;
        private String categorie;
        private LocalDateTime heureDebut;
        private LocalDateTime heureFin;
        private Integer dureeMinutes;
        private Integer difficulte;
        private String instructions;
        private List<String> ingredientsNecessaires;
    }
}