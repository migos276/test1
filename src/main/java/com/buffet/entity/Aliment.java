package com.buffet.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "aliments")
@Data
@EqualsAndHashCode(callSuper = false)
@EntityListeners(AuditingEntityListener.class)
public class Aliment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom de l'aliment est obligatoire")
    @Column(unique = true, nullable = false)
    private String nom;
    
    @Enumerated(EnumType.STRING)
    private PlatBuffet.CategorieAliment categorie;
    
    @ElementCollection
    @CollectionTable(name = "aliment_allergenes", joinColumns = @JoinColumn(name = "aliment_id"))
    @Column(name = "allergene")
    private List<String> allergenesCommuns;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Lob
    private byte[] image;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;
    
    @LastModifiedDate
    private LocalDateTime dateModification;
}