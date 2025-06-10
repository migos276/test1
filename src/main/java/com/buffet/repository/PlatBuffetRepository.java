package com.buffet.repository;

import com.buffet.entity.PlatBuffet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatBuffetRepository extends JpaRepository<PlatBuffet, Long> {
    
    List<PlatBuffet> findByBuffetId(Long buffetId);
    
    List<PlatBuffet> findByBuffetIdAndCategorie(Long buffetId, PlatBuffet.CategorieAliment categorie);
    
    @Query("SELECT p FROM PlatBuffet p WHERE p.buffet.id = :buffetId ORDER BY p.categorie, p.nomPlat")
    List<PlatBuffet> findByBuffetIdOrderByCategorieAndNom(@Param("buffetId") Long buffetId);
    
    @Query("SELECT SUM(p.coutUnitaire * p.buffet.nombreInvites) FROM PlatBuffet p WHERE p.buffet.id = :buffetId")
    Double calculateTotalCostByBuffetId(@Param("buffetId") Long buffetId);
    
    @Query("SELECT SUM(p.tempsPreparation) FROM PlatBuffet p WHERE p.buffet.id = :buffetId")
    Integer calculateTotalPreparationTimeByBuffetId(@Param("buffetId") Long buffetId);
}