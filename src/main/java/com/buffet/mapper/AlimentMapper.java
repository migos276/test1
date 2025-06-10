package com.buffet.mapper;

import com.buffet.dto.AlimentDto;
import com.buffet.entity.Aliment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AlimentMapper {
    
    @Mapping(target = "image", ignore = true)
    AlimentDto toDto(Aliment aliment);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    Aliment toEntity(AlimentDto alimentDto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    void updateEntityFromDto(AlimentDto dto, @MappingTarget Aliment entity);
}