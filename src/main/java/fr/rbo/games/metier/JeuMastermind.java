package fr.rbo.games.metier;

import fr.rbo.games.Main;
import fr.rbo.games.util.Outils;
import fr.rbo.games.ihm.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

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
        int r = 0;
        for (int i = 0; i < nbCases; i++) {
            if (valeurATrouver[i] == valeurProposee[i]) {
                r++;
            }
        }
        // recherche de l'ensemble des présents (bien placés compris)
        int b = -r;
        for (int i = 0; i < nbCouleurs; i++) {
            int n = 0;
            int m = 0;
            for (int j = 0; j < nbCases; j++) {
                if (valeurATrouver[j] == i) {
                    n++;
                }
                if (valeurProposee[j] == i) {
                    m++;
                }
            }
            if (n < m) {
                b = b + n;
            } else {
                b = b + m;
            }
        }
        int valScore = (10 * r) + b; // les présent sont sur la dizaine (r = rouge), les biens placés sur l'unitée (b = blanc)

        logger.debug("valeurATrouver : " + Arrays.toString(valeurATrouver).replace(", ", ""));
        logger.debug("valeurProposee : " + Arrays.toString(valeurProposee).replace(", ", ""));
        logger.debug("valScore        [" + valScore + "]");

     return valScore;
}

    /**
     * Proposition de l'ordinateur.
     * @param propositionPrecedente valeur de la proposition pr&eacute;c&eacute;dente.
     * @param resultat valeur de la comparaison pr&eacute;c&eacute;dente.
     * @return nombrePropose = Tableau de NB_DIGIT &eacute;l&eacute;ments num&eacute;riques, valeurs possibles 0 &agrave; 9.
     */
    protected int[] propositionOrdinateur(int[] propositionPrecedente, int resultat){
        int[] nombrePropose = new int[Main.NB_DIGIT_MASTERMIND];

        logger.debug("nombrePropose par l'ordinateur : " + Arrays.toString(nombrePropose).replace(", ", ""));

        return nombrePropose;
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
        int r, b = 0;
        int resultat2 = 0; // Pour la comparaison de la proposition de l'ordinateur
        int[] nombreATrouver = new int[Main.NB_DIGIT_MASTERMIND]; // Resultat du tirage aléatoire ou de la proposition humaine
        int[] propositionPrecedente =  new int[Main.NB_DIGIT_MASTERMIND]; // Permet de conserver la trace de la proposition précédente de l'ordinateur
        String solution;

        Outils outils = new Outils();
        Result result = new Result();

        switch (valModeJeu) {
            case "1": // Mode Challenger
                nombreATrouver = outils.tirageDuNombre(Main.NB_DIGIT_MASTERMIND, Main.NB_VALEURS_MASTERMIND, Main.DEBUG);

                do {
                    int[] nombreSaisi = outils.saisieNombre(Main.NB_DIGIT_MASTERMIND, Main.NB_VALEURS_MASTERMIND, PROPOSITION, Main.DEBUG);
//                    resultat = compareSaisie(nombreATrouver, nombreSaisi, Main.NB_DIGIT_MASTERMIND);
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
                System.out.println("Mastermind - Mode défenseur - ToDo");
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