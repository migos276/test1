# Système de Gestion de Buffets et Détection d'Allergies

## Description

Application Spring Boot complète pour la gestion de buffets et le suivi des allergies alimentaires.

## Fonctionnalités

### Gestion des Utilisateurs
- CRUD complet des utilisateurs
- Authentification sécurisée avec BCrypt
- Gestion des profils allergiques

### Gestion des Buffets
- Création et planification d'événements
- Gestion des plats par catégorie
- Calcul automatique des coûts et quantités
- Génération de planning de préparation

### Détection d'Allergies
- Suivi des repas et symptômes
- Base de données d'aliments et allergènes
- Analyse des corrélations alimentaires

## Architecture

### Couches de l'Application
- **Entities** : Modèles JPA avec validation
- **Repositories** : Accès aux données avec Spring Data JPA
- **Services** : Logique métier transactionnelle
- **Controllers** : API REST avec validation
- **DTOs** : Objets de transfert de données
- **Mappers** : Conversion avec MapStruct

### Technologies Utilisées
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring Security**
- **Spring Validation**
- **H2 Database** (développement)
- **PostgreSQL** (production)
- **MapStruct** (mapping)
- **Lombok** (réduction du code)

## Configuration

### Base de Données
- **Développement** : H2 en mémoire
- **Production** : PostgreSQL

### Profils Spring
- `default` : Configuration de développement
- `prod` : Configuration de production

## API Endpoints

### Utilisateurs
- `GET /api/utilisateurs` - Liste tous les utilisateurs
- `GET /api/utilisateurs/{id}` - Détails d'un utilisateur
- `POST /api/utilisateurs` - Créer un utilisateur
- `PUT /api/utilisateurs/{id}` - Modifier un utilisateur
- `DELETE /api/utilisateurs/{id}` - Supprimer un utilisateur

### Buffets
- `GET /api/buffets` - Liste tous les buffets
- `GET /api/buffets/{id}` - Détails d'un buffet
- `POST /api/buffets` - Créer un buffet
- `PUT /api/buffets/{id}` - Modifier un buffet
- `DELETE /api/buffets/{id}` - Supprimer un buffet
- `GET /api/buffets/{id}/details` - Détails complets avec plats
- `GET /api/buffets/{id}/quantites` - Calcul des quantités
- `GET /api/buffets/{id}/planning` - Planning de préparation

### Plats
- `POST /api/buffets/{buffetId}/plats` - Ajouter un plat au buffet

## Démarrage

### Prérequis
- Java 17+
- Maven 3.6+

### Lancement
```bash
mvn spring-boot:run
```

### Accès
- **Application** : http://localhost:8080
- **Console H2** : http://localhost:8080/h2-console
- **Actuator** : http://localhost:8080/actuator

## Tests

```bash
mvn test
```

## Sécurité

- Mots de passe chiffrés avec BCrypt
- Validation des données d'entrée
- Gestion des erreurs centralisée
- CORS configuré pour le développement

## Monitoring

- Spring Boot Actuator activé
- Endpoints de santé et métriques
- Logs configurés par environnement