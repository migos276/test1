package com.buffet.repository;

import com.buffet.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    
    Optional<Utilisateur> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM Utilisateur u LEFT JOIN FETCH u.buffets WHERE u.id = :id")
    Optional<Utilisateur> findByIdWithBuffets(@Param("id") Long id);
    
    @Query("SELECT u FROM Utilisateur u LEFT JOIN FETCH u.repas WHERE u.id = :id")
    Optional<Utilisateur> findByIdWithRepas(@Param("id") Long id);
}