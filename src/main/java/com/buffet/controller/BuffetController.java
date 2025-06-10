package com.buffet.controller;

import com.buffet.dto.BuffetDto;
import com.buffet.dto.PlatBuffetDto;
import com.buffet.dto.response.BuffetDetailsResponse;
import com.buffet.dto.response.PlanningPreparationResponse;
import com.buffet.dto.response.QuantitesBuffetResponse;
import com.buffet.service.BuffetService;
import com.buffet.service.PlatBuffetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buffets")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BuffetController {
    
    private final BuffetService buffetService;
    private final PlatBuffetService platBuffetService;
    
    @GetMapping
    public ResponseEntity<List<BuffetDto>> obtenirTousLesBuffets() {
        List<BuffetDto> buffets = buffetService.obtenirTousLesBuffets();
        return ResponseEntity.ok(buffets);
    }
    
    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<BuffetDto>> obtenirBuffetsParUtilisateur(@PathVariable Long utilisateurId) {
        List<BuffetDto> buffets = buffetService.obtenirBuffetsParUtilisateur(utilisateurId);
        return ResponseEntity.ok(buffets);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BuffetDto> obtenirBuffetParId(@PathVariable Long id) {
        BuffetDto buffet = buffetService.obtenirBuffetParId(id);
        return ResponseEntity.ok(buffet);
    }
    
    @PostMapping
    public ResponseEntity<BuffetDto> creerBuffet(@Valid @RequestBody BuffetDto buffetDto) {
        BuffetDto nouveauBuffet = buffetService.creerBuffet(buffetDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveauBuffet);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BuffetDto> mettreAJourBuffet(@PathVariable Long id, 
                                                       @Valid @RequestBody BuffetDto buffetDto) {
        BuffetDto buffetMisAJour = buffetService.mettreAJourBuffet(id, buffetDto);
        return ResponseEntity.ok(buffetMisAJour);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerBuffet(@PathVariable Long id) {
        buffetService.supprimerBuffet(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{buffetId}/plats")
    public ResponseEntity<PlatBuffetDto> ajouterPlatAuBuffet(@PathVariable Long buffetId,
                                                             @Valid @RequestBody PlatBuffetDto platDto) {
        PlatBuffetDto nouveauPlat = platBuffetService.ajouterPlatAuBuffet(buffetId, platDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveauPlat);
    }
    
    @GetMapping("/{buffetId}/details")
    public ResponseEntity<BuffetDetailsResponse> obtenirDetailsBuffet(@PathVariable Long buffetId) {
        BuffetDetailsResponse details = buffetService.obtenirDetailsBuffet(buffetId);
        return ResponseEntity.ok(details);
    }
    
    @GetMapping("/{buffetId}/quantites")
    public ResponseEntity<QuantitesBuffetResponse> calculerQuantitesBuffet(@PathVariable Long buffetId) {
        QuantitesBuffetResponse quantites = buffetService.calculerQuantitesBuffet(buffetId);
        return ResponseEntity.ok(quantites);
    }
    
    @GetMapping("/{buffetId}/planning")
    public ResponseEntity<PlanningPreparationResponse> genererPlanningPreparation(@PathVariable Long buffetId) {
        PlanningPreparationResponse planning = buffetService.genererPlanningPreparation(buffetId);
        return ResponseEntity.ok(planning);
    }
}