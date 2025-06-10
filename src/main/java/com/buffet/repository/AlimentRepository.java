package com.buffet.repository;

import com.buffet.entity.Aliment;
import com.buffet.entity.PlatBuffet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlimentRepository extends JpaRepository<Aliment, Long> {
    
    Optional<Aliment> findByNomIgnoreCase(String nom);
    
    List<Aliment> findByCategorie(PlatBuffet.CategorieAliment categorie);
    
    @Query("SELECT a FROM Aliment a WHERE LOWER(a.nom) LIKE LOWER(CONCAT('%', :nom, '%'))")
    List<Aliment> findByNomContainingIgnoreCase(@Param("nom") String nom);
    
    @Query("SELECT a FROM Aliment a JOIN a.allergenesCommuns ac WHERE ac = :allergene")
    List<Aliment> findByAllergenesCommuns(@Param("allergene") String allergene);
}