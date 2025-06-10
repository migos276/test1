package com.buffet.mapper;

import com.buffet.dto.UtilisateurDto;
import com.buffet.entity.Utilisateur;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {BuffetMapper.class, RepasMapper.class})
public interface UtilisateurMapper {
    
    @Mapping(target = "motDePasse", ignore = true)
    @Mapping(target = "photo", ignore = true)
    @Mapping(target = "buffets", ignore = true)
    @Mapping(target = "repas", ignore = true)
    UtilisateurDto toDto(Utilisateur utilisateur);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "motDePasse", ignore = true)
    @Mapping(target = "photo", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "buffets", ignore = true)
    @Mapping(target = "repas", ignore = true)
    Utilisateur toEntity(UtilisateurDto utilisateurDto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "motDePasse", ignore = true)
    @Mapping(target = "photo", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateModification", ignore = true)
    @Mapping(target = "buffets", ignore = true)
    @Mapping(target = "repas", ignore = true)
    void updateEntityFromDto(UtilisateurDto dto, @MappingTarget Utilisateur entity);
    
    @Mapping(target = "motDePasse", ignore = true)
    @Mapping(target = "photo", ignore = true)
    @Mapping(target = "repas", ignore = true)
    UtilisateurDto toDtoWithBuffets(Utilisateur utilisateur);
    
    @Mapping(target = "motDePasse", ignore = true)
    @Mapping(target = "photo", ignore = true)
    @Mapping(target = "buffets", ignore = true)
    UtilisateurDto toDtoWithRepas(Utilisateur utilisateur);
}