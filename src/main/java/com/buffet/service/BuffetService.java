package com.buffet.service;

import com.buffet.dto.BuffetDto;
import com.buffet.dto.PlatBuffetDto;
import com.buffet.dto.response.BuffetDetailsResponse;
import com.buffet.dto.response.PlanningPreparationResponse;
import com.buffet.dto.response.QuantitesBuffetResponse;
import com.buffet.entity.Buffet;
import com.buffet.entity.PlatBuffet;
import com.buffet.entity.Utilisateur;
import com.buffet.exception.ResourceNotFoundException;
import com.buffet.mapper.BuffetMapper;
import com.buffet.mapper.PlatBuffetMapper;
import com.buffet.repository.BuffetRepository;
import com.buffet.repository.PlatBuffetRepository;
import com.buffet.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BuffetService {
    
    private final BuffetRepository buffetRepository;
    private final PlatBuffetRepository platBuffetRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final BuffetMapper buffetMapper;
    private final PlatBuffetMapper platBuffetMapper;
    
    @Transactional(readOnly = true)
    public List<BuffetDto> obtenirTousLesBuffets() {
        return buffetRepository.findAll()
                .stream()
                .map(buffetMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<BuffetDto> obtenirBuffetsParUtilisateur(Long utilisateurId) {
        return buffetRepository.findByUtilisateurId(utilisateurId)
                .stream()
                .map(buffetMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public BuffetDto obtenirBuffetParId(Long id) {
        Buffet buffet = buffetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Buffet non trouvé avec l'ID: " + id));
        return buffetMapper.toDto(buffet);
    }
    
    public BuffetDto creerBuffet(BuffetDto buffetDto) {
        Utilisateur utilisateur = utilisateurRepository.findById(buffetDto.getUtilisateurId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec l'ID: " + buffetDto.getUtilisateurId()));
        
        Buffet buffet = buffetMapper.toEntity(buffetDto);
        buffet.setUtilisateur(utilisateur);
        
        Buffet buffetSauvegarde = buffetRepository.save(buffet);
        return buffetMapper.toDto(buffetSauvegarde);
    }
    
    public BuffetDto mettreAJourBuffet(Long id, BuffetDto buffetDto) {
        Buffet buffetExistant = buffetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Buffet non trouvé avec l'ID: " + id));
        
        buffetMapper.updateEntityFromDto(buffetDto, buffetExistant);
        Buffet buffetMisAJour = buffetRepository.save(buffetExistant);
        return buffetMapper.toDto(buffetMisAJour);
    }
    
    public void supprimerBuffet(Long id) {
        if (!buffetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Buffet non trouvé avec l'ID: " + id);
        }
        buffetRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public BuffetDetailsResponse obtenirDetailsBuffet(Long buffetId) {
        Buffet buffet = buffetRepository.findByIdWithPlats(buffetId)
                .orElseThrow(() -> new ResourceNotFoundException("Buffet non trouvé avec l'ID: " + buffetId));
        
        List<PlatBuffet> plats = platBuffetRepository.findByBuffetIdOrderByCategorieAndNom(buffetId);
        
        BuffetDetailsResponse response = new BuffetDetailsResponse();
        response.setBuffet(buffetMapper.toDto(buffet));
        
        // Organiser les plats par catégorie
        Map<String, List<PlatBuffetDto>> platsParCategorie = plats.stream()
                .collect(Collectors.groupingBy(
                        plat -> plat.getCategorie().name(),
                        Collectors.mapping(platBuffetMapper::toDto, Collectors.toList())
                ));
        response.setPlatsParCategorie(platsParCategorie);
        
        // Calculer le résumé
        BuffetDetailsResponse.ResumeBuffet resume = new BuffetDetailsResponse.ResumeBuffet();
        resume.setNombrePlats(plats.size());
        
        BigDecimal coutTotal = plats.stream()
                .map(plat -> plat.getCoutUnitaire().multiply(BigDecimal.valueOf(buffet.getNombreInvites())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        resume.setCoutEstime(coutTotal);
        
        Integer tempsTotal = plats.stream()
                .mapToInt(plat -> plat.getTempsPreparation() != null ? plat.getTempsPreparation() : 0)
                .sum();
        resume.setTempsPreparationTotal(tempsTotal);
        
        if (buffet.getNombreInvites() > 0) {
            resume.setCoutParPersonne(coutTotal.divide(BigDecimal.valueOf(buffet.getNombreInvites()), 2, RoundingMode.HALF_UP));
        } else {
            resume.setCoutParPersonne(BigDecimal.ZERO);
        }
        
        resume.setRespectBudget(buffet.getBudgetTotal() == null || 
                               coutTotal.compareTo(buffet.getBudgetTotal()) <= 0);
        
        response.setResume(resume);
        return response;
    }
    
    @Transactional(readOnly = true)
    public QuantitesBuffetResponse calculerQuantitesBuffet(Long buffetId) {
        Buffet buffet = buffetRepository.findById(buffetId)
                .orElseThrow(() -> new ResourceNotFoundException("Buffet non trouvé avec l'ID: " + buffetId));
        
        List<PlatBuffet> plats = platBuffetRepository.findByBuffetId(buffetId);
        
        Map<String, Double> ingredientsTotaux = new HashMap<>();
        
        for (PlatBuffet plat : plats) {
            if (plat.getIngredients() != null) {
                for (String ingredient : plat.getIngredients()) {
                    double quantiteUnitaire = plat.getQuantiteParPersonne() != null ? 
                                            plat.getQuantiteParPersonne() : 100.0; // défaut 100g
                    double quantiteTotale = quantiteUnitaire * buffet.getNombreInvites();
                    ingredientsTotaux.merge(ingredient, quantiteTotale, Double::sum);
                }
            }
        }
        
        QuantitesBuffetResponse response = new QuantitesBuffetResponse();
        response.setBuffetNom(buffet.getNomEvenement());
        response.setNombreInvites(buffet.getNombreInvites());
        response.setIngredientsTotaux(ingredientsTotaux);
        response.setDateEvenement(buffet.getDateEvenement());
        
        return response;
    }
    
    @Transactional(readOnly = true)
    public PlanningPreparationResponse genererPlanningPreparation(Long buffetId) {
        Buffet buffet = buffetRepository.findById(buffetId)
                .orElseThrow(() -> new ResourceNotFoundException("Buffet non trouvé avec l'ID: " + buffetId));
        
        List<PlatBuffet> plats = platBuffetRepository.findByBuffetId(buffetId);
        plats.sort((p1, p2) -> Integer.compare(
                p2.getTempsPreparation() != null ? p2.getTempsPreparation() : 0,
                p1.getTempsPreparation() != null ? p1.getTempsPreparation() : 0
        ));
        
        List<PlanningPreparationResponse.TachePreparation> planning = new ArrayList<>();
        LocalDateTime dateEvenement = buffet.getDateEvenement();
        
        for (PlatBuffet plat : plats) {
            int tempsPrep = plat.getTempsPreparation() != null ? plat.getTempsPreparation() : 30;
            LocalDateTime heureDebut = dateEvenement.minusMinutes(tempsPrep);
            
            PlanningPreparationResponse.TachePreparation tache = new PlanningPreparationResponse.TachePreparation();
            tache.setPlat(plat.getNomPlat());
            tache.setCategorie(plat.getCategorie().name());
            tache.setHeureDebut(heureDebut);
            tache.setHeureFin(dateEvenement);
            tache.setDureeMinutes(tempsPrep);
            tache.setDifficulte(plat.getDifficulte());
            tache.setInstructions(plat.getInstructionsPreparation());
            tache.setIngredientsNecessaires(plat.getIngredients());
            
            planning.add(tache);
        }
        
        List<String> recommandations = Arrays.asList(
                "Préparez les plats les plus complexes en premier",
                "Gardez les plats froids au réfrigérateur jusqu'au service",
                "Préparez une liste de vérification pour chaque plat",
                "Prévoyez 20% de nourriture en plus pour les imprévus"
        );
        
        PlanningPreparationResponse response = new PlanningPreparationResponse();
        response.setBuffetNom(buffet.getNomEvenement());
        response.setDateEvenement(buffet.getDateEvenement());
        response.setPlanningPreparation(planning);
        response.setTempsPreparationTotal(plats.stream()
                .mapToInt(plat -> plat.getTempsPreparation() != null ? plat.getTempsPreparation() : 0)
                .sum());
        response.setRecommandations(recommandations);
        
        return response;
    }
}