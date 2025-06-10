package com.buffet.mapper;

import com.buffet.dto.RepasDto;
import com.buffet.entity.Repas;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {SymptomeMapper.class})
public interface RepasMapper {
    
    @Mapping(source = "utilisateur.id", target = "utilisateurId")
    RepasDto toDto(Repas repas);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "utilisateur", ignore = true)
    @Mapping(target = "symptomes", ignore = true)
    Repas toEntity(RepasDto repasDto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "utilisateur", ignore = true)
    @Mapping(target = "symptomes", ignore = true)
    void updateEntityFromDto(RepasDto dto, @MappingTarget Repas entity);
}