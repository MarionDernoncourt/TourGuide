# Changelog

Toutes les modifications notables apportées au projet **TourGuide** sont documentées dans ce fichier.

Le format suit les principes de [Keep a Changelog](https://keepachangelog.com/fr/1.0.0/), et ce projet utilise la versioning sémantique.

## [Unreleased]

### Modifié
- **Test `nearAllAttractions`** : le test échouait aléatoirement à cause d’un problème de concurrence dans la méthode `calculateRewards(User user)` de `RewardsService`. Cette méthode a été modifiée pour paralléliser le calcul des récompenses avec un `ExecutorService` (`FixedThreadPool(10)`), puis `awaitTermination` pour garantir la complétion de toutes les tâches avant de poursuivre.
- **Classe `User`** :
  - `visitedLocations` et `userRewards` sont désormais instanciées avec `CopyOnWriteArrayList` pour améliorer la sécurité en environnement multi-thread.
- **Méthode `addUserReward(UserReward userReward)`** :
  - Ajout du mot-clé `synchronized` sur le bloc critique utilisant `userRewards.stream().anyMatch(...)` suivi de `add(...)`, afin d’éviter les accès concurrents non sécurisés.
- **Méthode `getNearByAttractions(VisitedLocation visitedLocation)` de `TourGuideService`** :
  - Modification de la logique de sélection des attractions : désormais, les 5 attractions les plus proches sont retournées, triées par distance, **sans filtrage préalable sur la distance maximale**.

  
### Ajouté

### Supprimé

### Corrigé
- **Bug dans la méthode `getNearByAttractions(...)`** : aucun résultat n’était retourné si aucune attraction n’était située dans la zone de proximité (définie par `proximityBuffer`). Ce comportement bloquait certaines fonctionnalités ; il a été corrigé en supprimant le filtre de distance au profit d’un tri global sur toutes les attractions.
- **Ajout de la classe `NearbyAttractionDTO`** pour encapsuler et structurer les données renvoyées par la méthode `getNearbyAttractions` dans le contrôleur, facilitant la sérialisation JSON et la gestion des réponses API.
- **Visibilité modifiée** : la méthode `getRewardsPoints()` dans `RewardsService` est passée de `private` à `public` pour permettre son utilisation dans d'autres classes (ex. dans le contrôleur ou lors des tests).

  