package com.buffet.service;

import com.buffet.dto.UtilisateurDto;
import com.buffet.entity.Utilisateur;
import com.buffet.exception.ResourceNotFoundException;
import com.buffet.mapper.UtilisateurMapper;
import com.buffet.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UtilisateurService {
    
    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMapper utilisateurMapper;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional(readOnly = true)
    public List<UtilisateurDto> obtenirTousLesUtilisateurs() {
        return utilisateurRepository.findAll()
                .stream()
                .map(utilisateurMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public UtilisateurDto obtenirUtilisateurParId(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));
        return utilisateurMapper.toDto(utilisateur);
    }
    
    @Transactional(readOnly = true)
    public UtilisateurDto obtenirUtilisateurParEmail(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'email: " + email));
        return utilisateurMapper.toDto(utilisateur);
    }
    
    public UtilisateurDto creerUtilisateur(UtilisateurDto utilisateurDto, String motDePasse) {
        if (utilisateurRepository.existsByEmail(utilisateurDto.getEmail())) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà");
        }
        
        Utilisateur utilisateur = utilisateurMapper.toEntity(utilisateurDto);
        utilisateur.setMotDePasse(passwordEncoder.encode(motDePasse));
        
        Utilisateur utilisateurSauvegarde = utilisateurRepository.save(utilisateur);
        return utilisateurMapper.toDto(utilisateurSauvegarde);
    }
    
    public UtilisateurDto mettreAJourUtilisateur(Long id, UtilisateurDto utilisateurDto) {
        Utilisateur utilisateurExistant = utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));
        
        // Vérifier si l'email est déjà utilisé par un autre utilisateur
        if (!utilisateurExistant.getEmail().equals(utilisateurDto.getEmail()) &&
            utilisateurRepository.existsByEmail(utilisateurDto.getEmail())) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà");
        }
        
        utilisateurMapper.updateEntityFromDto(utilisateurDto, utilisateurExistant);
        Utilisateur utilisateurMisAJour = utilisateurRepository.save(utilisateurExistant);
        return utilisateurMapper.toDto(utilisateurMisAJour);
    }
    
    public void supprimerUtilisateur(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id);
        }
        utilisateurRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public UtilisateurDto obtenirUtilisateurAvecBuffets(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findByIdWithBuffets(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));
        return utilisateurMapper.toDtoWithBuffets(utilisateur);
    }
    
    @Transactional(readOnly = true)
    public UtilisateurDto obtenirUtilisateurAvecRepas(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findByIdWithRepas(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + id));
        return utilisateurMapper.toDtoWithRepas(utilisateur);
    }
}