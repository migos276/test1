package com.buffet.mapper;

import com.buffet.dto.BuffetDto;
import com.buffet.entity.Buffet;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {PlatBuffetMapper.class})
public interface BuffetMapper {
    
    @Mapping(source = "utilisateur.id", target = "utilisateurId")
    @Mapping(target = "nombrePlats", expression = "java(buffet.getPlats() != null ? buffet.getPlats().size() : 0)")
    BuffetDto toDto(Buffet buffet);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "utilisateur", ignore = true)
    @Mapping(target = "plats", ignore = true)
    Buffet toEntity(BuffetDto buffetDto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "utilisateur", ignore = true)
    @Mapping(target = "plats", ignore = true)
    void updateEntityFromDto(BuffetDto dto, @MappingTarget Buffet entity);
}