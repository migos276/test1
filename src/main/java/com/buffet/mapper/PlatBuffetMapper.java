package com.buffet.mapper;

import com.buffet.dto.PlatBuffetDto;
import com.buffet.entity.PlatBuffet;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PlatBuffetMapper {
    
    @Mapping(source = "buffet.id", target = "buffetId")
    @Mapping(target = "coutTotal", expression = "java(calculateCoutTotal(platBuffet))")
    PlatBuffetDto toDto(PlatBuffet platBuffet);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "buffet", ignore = true)
    PlatBuffet toEntity(PlatBuffetDto platBuffetDto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "buffet", ignore = true)
    void updateEntityFromDto(PlatBuffetDto dto, @MappingTarget PlatBuffet entity);
    
    default java.math.BigDecimal calculateCoutTotal(PlatBuffet platBuffet) {
        if (platBuffet.getCoutUnitaire() != null && platBuffet.getBuffet() != null) {
            return platBuffet.getCoutUnitaire().multiply(
                java.math.BigDecimal.valueOf(platBuffet.getBuffet().getNombreInvites())
            );
        }
        return java.math.BigDecimal.ZERO;
    }
}