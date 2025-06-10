package com.buffet.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class QuantitesBuffetResponse {
    private String buffetNom;
    private Integer nombreInvites;
    private Map<String, Double> ingredientsTotaux;
    private LocalDateTime dateEvenement;
}