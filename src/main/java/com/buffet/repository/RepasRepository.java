package com.buffet.repository;

import com.buffet.entity.Repas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RepasRepository extends JpaRepository<Repas, Long> {
    
    List<Repas> findByUtilisateurId(Long utilisateurId);
    
    List<Repas> findByUtilisateurIdAndDateRepasBetween(Long utilisateurId, 
                                                       LocalDateTime debut, 
                                                       LocalDateTime fin);
    
    @Query("SELECT r FROM Repas r LEFT JOIN FETCH r.symptomes WHERE r.id = :id")
    Optional<Repas> findByIdWithSymptomes(@Param("id") Long id);
    
    @Query("SELECT r FROM Repas r WHERE r.utilisateur.id = :utilisateurId ORDER BY r.dateRepas DESC")
    List<Repas> findByUtilisateurIdOrderByDateRepasDesc(@Param("utilisateurId") Long utilisateurId);
}