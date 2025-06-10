package com.buffet.controller;

import com.buffet.dto.UtilisateurDto;
import com.buffet.dto.request.CreerUtilisateurRequest;
import com.buffet.service.UtilisateurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UtilisateurController {
    
    private final UtilisateurService utilisateurService;
    
    @GetMapping
    public ResponseEntity<List<UtilisateurDto>> obtenirTousLesUtilisateurs() {
        List<UtilisateurDto> utilisateurs = utilisateurService.obtenirTousLesUtilisateurs();
        return ResponseEntity.ok(utilisateurs);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDto> obtenirUtilisateurParId(@PathVariable Long id) {
        UtilisateurDto utilisateur = utilisateurService.obtenirUtilisateurParId(id);
        return ResponseEntity.ok(utilisateur);
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<UtilisateurDto> obtenirUtilisateurParEmail(@PathVariable String email) {
        UtilisateurDto utilisateur = utilisateurService.obtenirUtilisateurParEmail(email);
        return ResponseEntity.ok(utilisateur);
    }
    
    @PostMapping
    public ResponseEntity<UtilisateurDto> creerUtilisateur(@Valid @RequestBody CreerUtilisateurRequest request) {
        UtilisateurDto nouvelUtilisateur = utilisateurService.creerUtilisateur(
                request.getUtilisateur(), 
                request.getMotDePasse()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(nouvelUtilisateur);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDto> mettreAJourUtilisateur(@PathVariable Long id, 
                                                                 @Valid @RequestBody UtilisateurDto utilisateurDto) {
        UtilisateurDto utilisateurMisAJour = utilisateurService.mettreAJourUtilisateur(id, utilisateurDto);
        return ResponseEntity.ok(utilisateurMisAJour);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerUtilisateur(@PathVariable Long id) {
        utilisateurService.supprimerUtilisateur(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}/buffets")
    public ResponseEntity<UtilisateurDto> obtenirUtilisateurAvecBuffets(@PathVariable Long id) {
        UtilisateurDto utilisateur = utilisateurService.obtenirUtilisateurAvecBuffets(id);
        return ResponseEntity.ok(utilisateur);
    }
    
    @GetMapping("/{id}/repas")
    public ResponseEntity<UtilisateurDto> obtenirUtilisateurAvecRepas(@PathVariable Long id) {
        UtilisateurDto utilisateur = utilisateurService.obtenirUtilisateurAvecRepas(id);
        return ResponseEntity.ok(utilisateur);
    }
}