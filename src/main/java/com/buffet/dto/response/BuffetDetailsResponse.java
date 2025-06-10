package com.buffet.dto.response;

import com.buffet.dto.BuffetDto;
import com.buffet.dto.PlatBuffetDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class BuffetDetailsResponse {
    private BuffetDto buffet;
    private Map<String, List<PlatBuffetDto>> platsParCategorie;
    private ResumeBuffet resume;
    
    @Data
    public static class ResumeBuffet {
        private Integer nombrePlats;
        private BigDecimal coutEstime;
        private Integer tempsPreparationTotal;
        private BigDecimal coutParPersonne;
        private Boolean respectBudget;
    }
}