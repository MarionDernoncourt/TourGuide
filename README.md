# TourGuide - Optimisation de performances Java

> **Projet de formation : Spécialisation Java Avancé**
> *Focus : Programmation asynchrone, Multi-threading et scalabilité.*

## Le défi technique
Le projet initial était incapable de gérer la montée en charge. Le traitement séquentiel de 100 000 utilisateurs prenait plusieurs heures, ce qui étaitn inacceptable pour une application en temps réel.

## Les solutions implémentées
- **Refactoring asynchrone** : Migration des services vers un `CompletableFuture` pour libérer les threads bloqués.
- **Optimisation du Pool de Threads** : Mise en place d'un `ExecutorService` sur mesure pour équilibrer la charge CPU.
- **Sécurisation des données**: Utilisation de structures de données thread-safe pour garantir l'intégrité des calculs en parallèle.

## Résultats
<img width="939" height="581" alt="image" src="https://github.com/user-attachments/assets/cd48276b-afce-4e50-a022-777ad19b21a0" />
<img width="938" height="580" alt="image" src="https://github.com/user-attachments/assets/38544069-19f3-4609-a388-34f9b7e31a6a" />

