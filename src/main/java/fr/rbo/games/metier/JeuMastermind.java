package fr.rbo.games.metier;

import fr.rbo.games.Main;
import fr.rbo.games.util.Outils;
import fr.rbo.games.ihm.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Jeu de mastermind (recherche d'une s&eacute;rie de couleurs ou de chiffres)
 * et ses 3 modes de jeu Challenger, D&eacute;fenseur, Duel.
 * @author R&eacute;my Bourdoncle
 * @version 0.1
 */

public class JeuMastermind {

    private static final boolean PROPOSITION = true;
    private static final boolean SECRET = false;
    private static final boolean HUMAIN = true;
    private static final boolean ORDINATEUR = false;

    private static final Logger logger = LogManager.getLogger();

    List<String> listeCombinaisons = new ArrayList<String>();
    List<String> listePropositions = new ArrayList<String>();
    List<Integer> listeDesScores = new ArrayList<Integer>();

    /**
     * Création de la liste des combinaisons possible, cette liste permettra par élimination de découvrir les combinaisons possibles
     * @param nbCases nombre de case d'une ligne du plateau
     * @param nbCouleurs nombre de valeurs (couleurs) possibles
     */
    private void creationListeDesCombinaisons (int nbCases, int nbCouleurs){

        if (nbCouleurs <= 10 && nbCases <= 8) { // protection contre le dépassement de capacité
            double nbMax = Math.pow(nbCouleurs, nbCases);
            for (int i=0; i < nbMax; i++){
                listeCombinaisons.add((String.format("%0"+nbCases+"d", Integer.parseInt(Integer.toString(Integer.parseInt(String.valueOf(i), 10),nbCouleurs)))));
            }
        }
        logger.debug( "nbCases " + nbCases + ", nbCouleurs " + nbCouleurs + " listeCombinaisons" + listeCombinaisons);
    }

    /**
     * Comparaison de la combinaison secrete avec la proposition
     * @param valeurATrouver tableau contenant la combinaison secrete
     * @param valeurProposee tableau contenant la proposition du joueur
     * @param nbCases nombre de cases d'une ligne de jeu (taille de la combinaison)
     * @param nbCouleurs nombre de valeurs (couleurs) possibles
     * @return valScore les dizaines représentent le nombre de bien placés, les unités les présents
     */
    public int score(int[] valeurATrouver, int[] valeurProposee, int nbCases, int nbCouleurs) {

        // recherche des biens placés (rouge)
        int bienPlace = 0;
        for (int i = 0; i < nbCases; i++) {
            if (valeurATrouver[i] == valeurProposee[i]) {
                bienPlace++;
            }
        }
        // recherche de l'ensemble des correspondances (bien placés et présents)
        int present = -bienPlace;
        for (int i = 0; i < nbCouleurs; i++) {
            int positionDansATrouver = 0;
            int positionDansPropose = 0;
            for (int j = 0; j < nbCases; j++) {
                if (valeurATrouver[j] == i) {
                    positionDansATrouver++;
                }
                if (valeurProposee[j] == i) {
                    positionDansPropose++;
                }
            }
            if (positionDansATrouver < positionDansPropose) {
                present = present + positionDansATrouver;
            } else {
                present = present + positionDansPropose;
            }
        }
        int valScore = (10 * bienPlace) + present; // les présent sont sur la dizaine (r = rouge), les biens placés sur l'unitée (b = blanc)

        logger.debug("valeurATrouver : " + Arrays.toString(valeurATrouver).replace(", ", ""));
        logger.debug("valeurProposee : " + Arrays.toString(valeurProposee).replace(", ", "") + " valScore [" + valScore + "]");

     return valScore;
}

    /**
     * Recherche de la meilleure proposition pour le mode défenseur.
     * @param nbCases nombre de cases d'une ligne de jeu (taille de la combinaison)
     * @param nbCouleurs nombre de valeurs (couleurs) possibles
     * @return propositionAEvaluer = Tableau de NB_DIGIT &eacute;l&eacute;ments num&eacute;riques, combinaison à proposer par l'ordinateur.
     */
    protected int[] rechercheProposition(int nbCases, int nbCouleurs){
        int[] propositionAEvaluer = new int[nbCases];
        int[] propositionEvalueeHisto =  new int[nbCases];
        boolean scoreOK = false;
        int indiceListeDesPropositionsPossibles = 0;
        int indiceHistoriqueDesPropositionsTestees = 0;
        int scorePropositionATrouver = 0;
        double nbMax = 0;

        if (nbCouleurs <= 10 && nbCases <= 8) { // protection contre le dépassement de capacité
            nbMax = Math.pow(nbCouleurs, nbCases);
        }

        while ( indiceListeDesPropositionsPossibles < nbMax){
            // récupération des combinaisons de la liste des possibilitées
            for (int j = 0;j<Main.NB_DIGIT_MASTERMIND;j++){
                propositionAEvaluer[j] = (int) listeCombinaisons.get(indiceListeDesPropositionsPossibles).charAt(j) - 48;
            }
            scoreOK = true;
            indiceHistoriqueDesPropositionsTestees = 0;
            while ((indiceHistoriqueDesPropositionsTestees < listePropositions.size()) && (scoreOK)){
                for (int j = 0;j<Main.NB_DIGIT_MASTERMIND;j++){
                    propositionEvalueeHisto[j] = (int) listePropositions.get(indiceHistoriqueDesPropositionsTestees).charAt(j) - 48;
                }
                scorePropositionATrouver = score(propositionAEvaluer, propositionEvalueeHisto, Main.NB_DIGIT_MASTERMIND, Main.NB_VALEURS_MASTERMIND);
                if ((scorePropositionATrouver == listeDesScores.get(indiceHistoriqueDesPropositionsTestees)) && (scoreOK)) {
                    scoreOK = true;
                } else {
                    scoreOK = false;
                }
                indiceHistoriqueDesPropositionsTestees++;
            }
            if (scoreOK) {
                logger.debug("nombrePropose par l'ordinateur (sortie 1): " + Arrays.toString(propositionAEvaluer).replace(", ", ""));
                return propositionAEvaluer;
            }
            indiceListeDesPropositionsPossibles++;
        }
    logger.debug("nombrePropose par l'ordinateur (sortie 2): " + Arrays.toString(propositionAEvaluer).replace(", ", ""));
    return propositionAEvaluer;
}

    /**
     * Lancement du jeu Mastermind.
     * @param valModeJeu choix du mode de jeu.
     * @return rapport compte rendu du r&eacute;sultat de la partie.
     */
    public String lanceMastermind(String valModeJeu) {
        String rapport = "ToDo";
        int nbrCoups = 0;
        int resultat = 0;
        int resultat2 = 0; // Pour la comparaison de la proposition de l'ordinateur
        int r, b = 0; // r = nombre de rouges (=biens placés), b = nombre de blancs (=présents)
        int[] nombreATrouver = new int[Main.NB_DIGIT_MASTERMIND]; // Resultat du tirage aléatoire ou de la proposition humaine
        int[] nombreATrouverParOrdinateur = new int[Main.NB_DIGIT_MASTERMIND]; // Valeur de la proposition humaine
        int[] nombreSaisi =  new int[Main.NB_DIGIT_MASTERMIND]; // Saisie de la proposition de l'utilisateur
        int[] nombrePropose =  new int[Main.NB_DIGIT_MASTERMIND]; // Proposition de l'ordinateur

        Outils outils = new Outils();
        Result result = new Result();

        switch (valModeJeu) {
            case "1": // Mode Challenger
                nombreATrouver = outils.tirageDuNombre(Main.NB_DIGIT_MASTERMIND, Main.NB_VALEURS_MASTERMIND, Main.DEBUG);
                do {
                    nombreSaisi = outils.saisieNombre(Main.NB_DIGIT_MASTERMIND, Main.NB_VALEURS_MASTERMIND, PROPOSITION, Main.DEBUG);
                    resultat = score(nombreATrouver, nombreSaisi, Main.NB_DIGIT_MASTERMIND, Main.NB_VALEURS_MASTERMIND);
                    r = resultat/10;
                    b = resultat%10;
                    result.afficheResultat(HUMAIN, r, b , nombreSaisi, "");
                    nbrCoups++;
                }
                while ((r != Main.NB_DIGIT_MASTERMIND) && (nbrCoups < Main.ESSAIS_MAX_MASTERMIND));
                if (r == Main.NB_DIGIT_MASTERMIND){
                    rapport = "Gagné en " + nbrCoups + " coups. Solution ";
                } else {
                    rapport = "Perdu, nombre de tentatives (" + nbrCoups + ") atteint, la solution est : ";
                }
                rapport = rapport + Arrays.toString(nombreATrouver).replace(", ", "") + ".";
                break;

            case "2": // Mode défenseur
                creationListeDesCombinaisons(Main.NB_DIGIT_MASTERMIND, Main.NB_VALEURS_MASTERMIND);
                nombreATrouverParOrdinateur = outils.saisieNombre(Main.NB_DIGIT_MASTERMIND, Main.NB_VALEURS_MASTERMIND, PROPOSITION, Main.DEBUG);
                double der = Math.pow(Main.NB_VALEURS_MASTERMIND, Main.NB_DIGIT_MASTERMIND)-1;

                do {
                    if (nbrCoups == 0){ // au 1er passage on tire un nombre aléatoire pour commencer à chercher
                        nombrePropose = outils.tirageDuNombre(Main.NB_DIGIT_MASTERMIND, Main.NB_VALEURS_MASTERMIND, Main.DEBUG);
                    } else {
                        nombrePropose = rechercheProposition(Main.NB_DIGIT_MASTERMIND, Main.NB_VALEURS_MASTERMIND);
                    }
                    String cList = "";
                    for (int i = 0; i < Main.NB_DIGIT_MASTERMIND; i++){
                        cList = cList + nombrePropose[i];
                    }
                    listePropositions.add(cList); // Sauvegarde de la proposition
                    resultat2 = score(nombreATrouverParOrdinateur, nombrePropose, Main.NB_DIGIT_MASTERMIND, Main.NB_VALEURS_MASTERMIND);
                    listeDesScores.add(resultat2);
                    nbrCoups++;

                    logger.debug("         Proposition N° " + nbrCoups + " : " + listePropositions.get(nbrCoups-1) + " score : " + listeDesScores.get(nbrCoups-1));

                    r = resultat2/10;
                    b = resultat2%10;
                    result.afficheResultat(ORDINATEUR, r, b , nombrePropose, "");
                }
                while ((r != Main.NB_DIGIT_MASTERMIND) && (nbrCoups < Main.ESSAIS_MAX_MASTERMIND));
                if (r == Main.NB_DIGIT_MASTERMIND){
                    rapport = "L'ordinateur gagne, il a trouvé en " + nbrCoups;
                } else {
                    rapport = "Vous gagnez, l'ordinateur n'a pas trouvé en " + nbrCoups;
                }
                rapport = rapport + " coups votre combinaison secrete : " + Arrays.toString(nombreATrouverParOrdinateur).replace(", ", "");
                break;

            case "3": // Mode Duel
                System.out.println("Mastermind - Mode duel - ToDo");
                break;

            default:
                System.out.println("ERREUR valeur incorrecte !");
                break;
        }
        return rapport;
    }
}