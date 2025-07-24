# 📍 TourGuide — Documentation Technique

## 🧭 Aperçu de l’application

**TourGuide** est une application Spring Boot développée par notre entreprise. Elle permet aux utilisateurs :
- de consulter les attractions touristiques à proximité,
- d’obtenir des réductions sur les séjours à l’hôtel et sur des billets de spectacles.

Jusqu’ici, TourGuide recevait quelques centaines d’utilisateurs par jour. Toutefois, un article dans un magazine de voyages a entraîné un pic de fréquentation : **30 000 nouveaux utilisateurs** en 2 mois, et une projection à **100 000 utilisateurs par jour** dans un futur proche.

Cette croissance soudaine a mis en lumière des **problèmes de performance et de fiabilité** qui doivent désormais être traités.

---

## 🚨 Problèmes identifiés

### 💡 Problèmes de performance

L'application est devenue **trop lente** pour supporter la charge actuelle. Deux services externes sont particulièrement critiques :

- `gpsUtil` : trop lent à haute charge pour localiser les utilisateurs.
- `RewardsCentral` : réponse imprévisible, parfois plusieurs jours pour délivrer les récompenses.

Un fichier Excel (non inclus ici) montre les temps de réponse sous stress test, avec un espace réservé pour y insérer les résultats post-correction.

### 🐞 Bugs fonctionnels

- Certains tests unitaires échouent **aléatoirement**, sans cause apparente.
- Les utilisateurs **ne reçoivent pas** toujours les recommandations d’attractions.

---

## ✅ Tâches critiques à réaliser

1. **Corriger les tests unitaires instables.**
2. **Corriger la logique de recommandations** : chaque utilisateur doit recevoir **les 5 attractions les plus proches**, sans contrainte de distance.
3. **Optimiser l’appel à `gpsUtil`** : 100 000 positions doivent être récupérées en **moins de 15 minutes**.
4. **Optimiser l’appel à `RewardsCentral`** : obtenir les récompenses de 100 000 utilisateurs en **moins de 20 minutes**.
5. **Aligner les tests de performance** avec les optimisations réalisées.
6. **Mettre en place un pipeline d’intégration continue** : compilation, exécution des tests et génération d’un JAR exécutable téléchargeable.

---

## 🛠️ Recommandations

- documenter **chaque changement** technique pour assurer la continuité de l’équipe,
- choisir librement l’outil CI/CD (GitHub Actions, GitLab-CI, etc.) pour automatiser le build.

> ✍️ Cette documentation évoluera **au fil des tâches réalisées**.
> Consultez le [changelog](changelog.md) pour suivre les modifications majeures apportées à l'application.

---

## 🔗 Ressources complémentaires

- `README.md` (racine du projet) : vue d’ensemble rapide.
- `docs/changelog.md` : historique des modifications.
- `docs/performance_tests.md` *(à créer)* : résultats des tests avant/après optimisation.
