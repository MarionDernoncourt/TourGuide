# Changelog

Toutes les modifications notables apportées au projet **TourGuide** sont documentées dans ce fichier.

Le format suit les principes de [Keep a Changelog](https://keepachangelog.com/fr/1.0.0/), et ce projet utilise la versioning sémantique.

## [Unreleased]

### Modifié
- **Methode `RewardsService.calculateRewards(User user)`**: les listes `visitedLocation` et `attractions` sont maintenant de `CopyOnWriteArrayList` pour garantir la sécurité thread-safe et éviter les ConcurrentModificationsException lors du calcul des récompenses.
- **Méthode `getNearByAttractions(VisitedLocation visitedLocation)` de `TourGuideService`** : Modification de la logique de sélection des attractions : désormais, les 5 attractions les plus proches sont retournées, triées par distance, sans filtrage préalable sur la distance maximale.
- **Test `highVolumeTrackLocation` dans `TestPerformance`** : modifié pour utiliser trackAllUserLocation() au lieu d'itérer sur tous les utilisateurs avec `trackUserLocation` de façon séquentielle afin d'améliorer la performance.
- **Test `highVolumeGetRewards` dans `TestPerformance`** :  Refactoré pour utiliser `calculateRewardsForAllUsers()` au lieu de boucler sur chaque utilisateur avec `calculateRewards()` de manière séquentielle, réduisant le temps de calcule des récompenses pour un grand nombre d'utilisateurs.

  
### Ajouté
- **Ajout de la classe `NearbyAttractionDTO`** pour encapsuler et structurer les données renvoyées par la méthode `getNearbyAttractions` dans le contrôleur, facilitant la sérialisation JSON et la gestion des réponses API.
- **Méthode `trackAllUserLocation()` dans `TourGuideService`** : permet de suivre la localisation de tous les utilisateurs de manière asynchrone avec `CompletableFuture` et `ExecutorService`, améliorant les performances par rapport à l'itération séquentielle dans le test de performance.
- **Méthode `calculateRewardsForAllUsers(List<User> users)` dans `RewardsService`** : permet de calculer les récompenses de tous les utilisateurs de manière asynchrone avec `CompletableFuture` et un `ExecutorService`, améliorant fortement les performances par rapport à l'itération séquentielle sur chaque utilisateur dans le test de performance.
- ** Mise en place d'une pipeline d'intégration continue (Github Action)** : Compilation du projet avec Maven, Exécution des tests unitaires (tests de performance exclus), packaginig du JAR exécutable et upload du JAR en artéfact téléchargeable

### Supprimé

### Corrigé
- **Méthode `User.addUserReward(UserReward userReward)`** : correction du filtrage pour vérifier l'existence d'une récompense avant l'ajout en comparant correctionement les IDs ou noms des attractions. Cela permet d'ajouter toute les récompenses prévues et fait passer le test `nearAllAttractions()` (26 récompenses au lieu de 1).
- **Visibilité modifiée** : la méthode `getRewardsPoints()` dans `RewardsService` est passée de `private` à `public` pour permettre son utilisation dans d'autres classes (ex. dans le contrôleur ou lors des tests).

  