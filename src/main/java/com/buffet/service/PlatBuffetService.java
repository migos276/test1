package com.buffet.service;

import com.buffet.dto.PlatBuffetDto;
import com.buffet.entity.Buffet;
import com.buffet.entity.PlatBuffet;
import com.buffet.exception.ResourceNotFoundException;
import com.buffet.mapper.PlatBuffetMapper;
import com.buffet.repository.BuffetRepository;
import com.buffet.repository.PlatBuffetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PlatBuffetService {
    
    private final PlatBuffetRepository platBuffetRepository;
    private final BuffetRepository buffetRepository;
    private final PlatBuffetMapper platBuffetMapper;
    
    @Transactional(readOnly = true)
    public List<PlatBuffetDto> obtenirPlatsParBuffet(Long buffetId) {
        return platBuffetRepository.findByBuffetIdOrderByCategorieAndNom(buffetId)
                .stream()
                .map(platBuffetMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public PlatBuffetDto obtenirPlatParId(Long id) {
        PlatBuffet plat = platBuffetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plat non trouvé avec l'ID: " + id));
        return platBuffetMapper.toDto(plat);
    }
    
    public PlatBuffetDto ajouterPlatAuBuffet(Long buffetId, PlatBuffetDto platDto) {
        Buffet buffet = buffetRepository.findById(buffetId)
                .orElseThrow(() -> new ResourceNotFoundException("Buffet non trouvé avec l'ID: " + buffetId));
        
        PlatBuffet plat = platBuffetMapper.toEntity(platDto);
        plat.setBuffet(buffet);
        
        PlatBuffet platSauvegarde = platBuffetRepository.save(plat);
        return platBuffetMapper.toDto(platSauvegarde);
    }
    
    public PlatBuffetDto mettreAJourPlat(Long id, PlatBuffetDto platDto) {
        PlatBuffet platExistant = platBuffetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plat non trouvé avec l'ID: " + id));
        
        platBuffetMapper.updateEntityFromDto(platDto, platExistant);
        PlatBuffet platMisAJour = platBuffetRepository.save(platExistant);
        return platBuffetMapper.toDto(platMisAJour);
    }
    
    public void supprimerPlat(Long id) {
        if (!platBuffetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Plat non trouvé avec l'ID: " + id);
        }
        platBuffetRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<PlatBuffetDto> obtenirPlatsParCategorie(Long buffetId, PlatBuffet.CategorieAliment categorie) {
        return platBuffetRepository.findByBuffetIdAndCategorie(buffetId, categorie)
                .stream()
                .map(platBuffetMapper::toDto)
                .collect(Collectors.toList());
    }
}