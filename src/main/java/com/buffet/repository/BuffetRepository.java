package com.buffet.repository;

import com.buffet.entity.Buffet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BuffetRepository extends JpaRepository<Buffet, Long> {
    
    List<Buffet> findByUtilisateurId(Long utilisateurId);
    
    List<Buffet> findByUtilisateurIdAndStatut(Long utilisateurId, Buffet.StatutBuffet statut);
    
    @Query("SELECT b FROM Buffet b WHERE b.dateEvenement BETWEEN :debut AND :fin")
    List<Buffet> findByDateEvenementBetween(@Param("debut") LocalDateTime debut, 
                                           @Param("fin") LocalDateTime fin);
    
    @Query("SELECT b FROM Buffet b LEFT JOIN FETCH b.plats WHERE b.id = :id")
    Optional<Buffet> findByIdWithPlats(@Param("id") Long id);
    
    @Query("SELECT COUNT(b) FROM Buffet b WHERE b.utilisateur.id = :utilisateurId")
    Long countByUtilisateurId(@Param("utilisateurId") Long utilisateurId);
}