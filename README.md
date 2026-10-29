# GO-SECURI

**Une application de bureau pour suivre les équipements attribués aux agents de sécurité.**

![Java](https://img.shields.io/badge/Java-Desktop-ED8B00)
![JavaFX](https://img.shields.io/badge/Interface-JavaFX-0078D4)
![Firestore](https://img.shields.io/badge/Database-Cloud_Firestore-FFCA28?logo=firebase&logoColor=black)
![Maven](https://img.shields.io/badge/Build-Maven-C71A36?logo=apachemaven&logoColor=white)
![Status](https://img.shields.io/badge/Statut-Prototype-orange)

## Présentation

GO-SECURI est un projet d’application Java consacré à la gestion du matériel d’une équipe de sécurité. Son objectif : permettre à un agent de consulter ses équipements, d’en retirer de nouveaux et de les restituer, tout en mettant à jour les quantités disponibles dans un stock partagé.

Le projet associe une interface **JavaFX**, des écrans décrits en **FXML** et une persistance dans **Cloud Firestore**, accessible via le SDK Firebase Admin.

Le dépôt regroupe une application principale et plusieurs prototypes d’interface. Le module de référence est **`MSPR-Project/`**.

## Cas d’usage

Avant une intervention, un agent doit pouvoir identifier le matériel disponible et enregistrer les équipements qu’il emporte. À son retour, il doit pouvoir signaler leur restitution pour les rendre à nouveau disponibles.

Le parcours implémenté suit cette logique :

1. Charger un profil d’agent depuis Firestore.
2. Afficher la liste des équipements et présélectionner ceux déjà attribués.
3. Désactiver, à l’ouverture de l’écran, les équipements épuisés que l’agent ne possède pas.
4. Cocher un équipement pour réduire son stock et l’ajouter au profil de l’agent.
5. Décocher un équipement pour augmenter son stock et le retirer du profil.

Les références présentes dans l’interface comprennent notamment des gants d’intervention, lampes torches, brassards, kits oreillettes, gilets et talkies-walkies.

## Fonctionnalités et état actuel

| Fonctionnalité | Implémentation présente |
| --- | --- |
| Interface de bureau | Écrans JavaFX d’entrée et de gestion du matériel. |
| Profil agent | Lecture des informations et de la liste des équipements dans Firestore. |
| Attribution et restitution | Mise à jour des quantités et des équipements associés à l’agent. |
| Disponibilité | Désactivation initiale des équipements sans stock, sauf ceux déjà détenus. |
| Persistance | Lecture et écriture dans les collections Firestore. |
| Capture vidéo | Dépendance OpenCV et code exploratoire, actuellement désactivé. |

**La version actuelle est un prototype.** L’écran d’entrée charge un prénom de démonstration défini dans le contrôleur ; il ne réalise pas d’authentification utilisateur. La reconnaissance faciale n’est pas implémentée.

Les mises à jour du stock et du profil sont réalisées séparément. La gestion des accès simultanés, des erreurs et des profils absents doit être renforcée avant un usage opérationnel.

## Stack technique

| Couche | Technologies |
| --- | --- |
| Langage | Java |
| Interface | JavaFX Controls, JavaFX FXML |
| Persistance | Cloud Firestore |
| Accès aux services Google | Firebase Admin SDK 6.8.1 |
| Compilation et lancement | Maven, JavaFX Maven Plugin |
| Expérimentation vidéo | OpenCV 3.2 |
| Prototypes complémentaires | Swing, NetBeans, composants de listes à cocher |

Le module principal déclare également une dépendance Spring Boot Web. Son point d’entrée reste une application JavaFX ; aucune API REST métier n’y est exposée.

## Organisation du dépôt

| Chemin | Rôle |
| --- | --- |
| `MSPR-Project/` | Application principale JavaFX et configuration Maven. |
| `MSPR-Project/src/main/java/Main.java` | Point d’entrée et initialisation de l’application. |
| `MSPR-Project/src/main/java/FXControllers/` | Contrôleurs des écrans d’entrée et de stock. |
| `MSPR-Project/src/main/java/Services/` | Initialisation Firebase et accès aux données Firestore. |
| `MSPR-Project/src/main/java/EssentialClasses/` | Modèles `User` et `Item`. |
| `MSPR-Project/src/main/java/Utils/` | Utilitaires JavaFX et conversion d’images OpenCV. |
| `MSPR-Project/src/main/resources/` | Écrans FXML et ressources graphiques. |
| `MSPR-JAVAFX/`, `pages-FXML/` | Prototypes et variantes des écrans JavaFX. |
| `MSPR_APPLOURDE/`, `TestJavaNetbeansGUI/` | Prototypes Swing et structures exploratoires. |
| `CheckBoxListProject/`, `Composant-test/` | Expérimentations autour de composants graphiques. |

## Modèle de données

### Agents

Les documents agents sont lus dans la sous-collection :

```text
users/Desktop/Employe/{agentId}
```

Le modèle `User` contient notamment :

| Champ Java | Rôle |
| --- | --- |
| `documentID` | Identifiant porté par le modèle. |
| `prenom` | Prénom utilisé pour rechercher le profil de démonstration. |
| `nom` | Nom utilisé lors de la mise à jour de l’attribution. |
| `photo` | Référence de photo prévue dans le modèle. |
| `equipement` | Liste des identifiants des équipements attribués. |

### Stock

Les équipements sont lus dans :

```text
local/{itemId}
```

Le service utilise les champs Firestore **`Nom`** pour la référence du matériel et **`Num`** pour sa quantité disponible. Les références doivent correspondre aux identifiants des cases à cocher définies dans `StockPage.fxml` et aux valeurs de la liste `equipement`.

## Configuration et démarrage

### Prérequis

- Maven et un JDK compatibles avec la configuration JavaFX du module.
- Un projet Firebase personnel disposant d’une base Cloud Firestore.
- Un compte de service autorisé à accéder à cette base.
- Des données de démonstration correspondant au modèle attendu.

Le `pom.xml` mélange une cible de compilation Java 8 et des dépendances JavaFX 11/14. L’environnement Java doit donc être harmonisé avant de considérer le lancement comme reproductible. Le dépôt contient également des bibliothèques JavaFX natives pour macOS ; leur présence ne garantit pas la compatibilité avec une autre plateforme.

### 1. Configurer Firebase

Dans `MSPR-Project/src/main/java/Services/FirebaseInitialize.java` :

1. Remplacer l’URL Firebase historique par celle de son propre projet.
2. Configurer le chargement de ses propres identifiants de compte de service.
3. Vérifier que le compte utilisé dispose uniquement des permissions nécessaires.

Le code actuel cherche un fichier `accountServiceKey.json` dans le répertoire de travail du processus. Pour conserver ce fonctionnement en local, placer sa propre clé dans `MSPR-Project/` et l’exclure de Git :

```gitignore
# Identifiants Firebase locaux
accountServiceKey.json
```

Ne pas publier de clé de compte de service. Si une clé a déjà été exposée, la révoquer et la remplacer ; supprimer le fichier du dernier commit ne suffit pas à retirer les versions de l’historique.

### 2. Préparer les données

Créer les collections agents et stock décrites ci-dessus. Initialiser la liste `equipement`, y compris lorsqu’elle est vide, et utiliser des quantités entières pour `Num`.

Dans `FXControllers/LoginPageController.java`, adapter le prénom de démonstration à un agent présent dans la base. La recherche d’utilisateur et le traitement des profils absents restent à fiabiliser.

### 3. Compiler et lancer

Depuis la racine du dépôt, une fois la configuration adaptée :

```bash
cd MSPR-Project
mvn compile
mvn javafx:run
```

La classe principale configurée est `Main`. Les commandes ci-dessus constituent le point d’entrée Maven ; leur exécution dépend de l’alignement des versions Java/JavaFX et de la résolution des dépendances historiques. Le packaging d’un installateur nécessite également une validation spécifique de sa configuration.

## Architecture applicative

Les écrans FXML délèguent les interactions aux contrôleurs JavaFX. Ces contrôleurs appellent `FirebaseService`, qui lit les profils et les stocks, puis écrit les changements dans Firestore. Les classes `User` et `Item` portent les données échangées.

Cette version accède directement à Firestore avec le SDK Admin. Une évolution destinée à être distribuée à plusieurs utilisateurs devra déplacer les opérations privilégiées vers un service backend et mettre en place une authentification et des autorisations adaptées.

## Évolutions prévues

- [ ] Remplacer le profil de démonstration par une authentification utilisateur.
- [ ] Séparer l’application de bureau des accès privilégiés à Firestore.
- [ ] Regrouper les modifications de stock et d’attribution dans des transactions.
- [ ] Recontrôler la disponibilité au moment du retrait.
- [ ] Corriger la recherche d’agents et gérer les données absentes.
- [ ] Charger le catalogue d’équipements dynamiquement depuis la base.
- [ ] Exécuter les appels réseau hors du thread graphique JavaFX.
- [ ] Ajouter un historique des retraits et des restitutions.
- [ ] Harmoniser les versions Java/JavaFX et simplifier le dépôt.
- [ ] Ajouter des tests métier et automatiser la compilation.
