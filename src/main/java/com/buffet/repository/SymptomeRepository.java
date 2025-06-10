package com.buffet.repository;

import com.buffet.entity.Symptome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SymptomeRepository extends JpaRepository<Symptome, Long> {
    
    List<Symptome> findByRepasId(Long repasId);
    
    List<Symptome> findByRepasUtilisateurId(Long utilisateurId);
    
    List<Symptome> findByRepasUtilisateurIdAndDateApparitionBetween(Long utilisateurId, 
                                                                   LocalDateTime debut, 
                                                                   LocalDateTime fin);
    
    @Query("SELECT s FROM Symptome s WHERE s.repas.utilisateur.id = :utilisateurId AND s.severite = :severite")
    List<Symptome> findByUtilisateurIdAndSeverite(@Param("utilisateurId") Long utilisateurId, 
                                                  @Param("severite") Symptome.Severite severite);
}