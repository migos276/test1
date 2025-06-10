package com.buffet.mapper;

import com.buffet.dto.SymptomeDto;
import com.buffet.entity.Symptome;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SymptomeMapper {
    
    @Mapping(source = "repas.id", target = "repasId")
    SymptomeDto toDto(Symptome symptome);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "repas", ignore = true)
    Symptome toEntity(SymptomeDto symptomeDto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "repas", ignore = true)
    void updateEntityFromDto(SymptomeDto dto, @MappingTarget Symptome entity);
}