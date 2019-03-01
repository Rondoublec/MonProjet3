# MonProjet3
Projet 3 - Réaliser une application de jeux en Java, Recherche plus ou moins et Mastermind, avec Log4j V2

* jeu de recherche d'une combinaison secrète grâce à des indications + ou -
* jeu de Mastermind

Avec 3 modes de jeu :
* Challenger où l’utilisateur doit trouver la proposition de l’ordinateur
* Défenseur où l’ordinateur doit trouver la proposition de l’utilisateur
* Duel où l'ordinateur et l’utilisateur jouent tour à tour, le premier (à nombre de tentatives égal) à trouver la combinaison secrète de l'autre gagne.

## Prérequis
Java 8 minimum, dans les lignes de commandes d'exemples on suppose que le chemin de java.exe est présent dans le path du système (sous Windows). Si ce n'est pas le cas l'ajouter ou entrez le chemin complet, en substituant <b>"java"</b> par le chemin correspondant à votre installation de java, par exemple dans mon cas ce serait <b>"C:\Program Files\Java\jdk1.8.0_66\bin\java.exe"</b>.

## Lancement
Le répertoire bin contient :
Le jar <b>games.jar</b> dans lequel sont intégrés toutes les librairies nécessaires, pour le lancer :
* <b>java -jar games.jar </b>

Le <b>log4j2.xml</b> qui permet de gérer les log, la version embarquée dans le jar ne prend aucun log, si vous voulez tracer il faut lancer le jar en précisant de prendre ce fichier en paramètres avec la commande :

* <b>java -Dlog4j.configurationFile=file:///c:/games/log4j2.xml -jar games.jar</b>

Le <b>config.properties</b> qui contient les paramètres des jeux, ci-dessous les explications :
* nbCasesPlusMoins=4
* nbEssaisPlusMoins=5
* nbValeursPlusMoins=10 (valeurs pour chaque chiffre de 0 à 9)
* nbCasesMastermind=4
* nbEssaisMastermind=8
* nbValeursMastermind=6 (soit 6 couleurs matérialisées par des valeurs pour chaque chiffre de 0 à 5, minimum 6 / maximum 10)
* modeDev=false (mode développeur pour afficher la solution à trouver <b>false</b> ou <b>true</b>)

Il est aussi possible d'activer le mode développeur en lançant : <b>java -jar MesJeux.jar dev </b> et de le désactiver avec : <b>java -jar MesJeux.jar nodev</b>.

Des lanceurs d'exemples (.bat) avec différentes options sont disponibles dans le répertoire bin.

## Divers
Contenu des dossiers
* target\ jar et config
* doc\ Documentation
* src\ Sources du projet (développé en utilisant IntelliJ)
* test\ Tests unitaires (JUnit)

