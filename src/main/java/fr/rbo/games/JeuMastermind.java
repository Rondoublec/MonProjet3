package fr.rbo.games;

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
    private int present = 0;
    private int bienPlace = 0;
    private String votreSaisie = "";

    private static final Logger logger = LogManager.getLogger();

    /**
     * Comparaison de la s&eacute;rie &agrave; trouver et de la s&eacute;rie propos&eacute;e par le joueur.
     *
     * @param valeurATrouver s&eacute;rie &agrave; trouver.
     * @param valeurSaisi s&eacute;rie propos&eacute;e par le joueur.
     * @param nbOccurences nombre de caractères / digits dont est constituée la chaine.
     * @return resultat = Tableau de NB_DIGIT &eacute;l&eacute;ments de la comparaison chiffre &agrave; chiffre, valeurs possibles =, -, +.
     */
    public int compareSaisie(int[] valeurATrouver, int[] valeurSaisi, int nbOccurences) {
        int[] traiteATrouver = new int[nbOccurences];
        int[] traiteSaisi = new int[nbOccurences];

        present = 0;
        bienPlace = 0;

        logger.debug("Solution = " + Arrays.toString(valeurATrouver).replace(", ", ""));

        for (int i=0; i < nbOccurences; i++) {
            if (valeurSaisi[i] == valeurATrouver[i]) {
                bienPlace = bienPlace + 1;
                traiteSaisi[i] = -1;
                traiteATrouver[i] = -1;
            }
        }
        if (bienPlace < nbOccurences) {
            for (int i = 0; i < nbOccurences; i++) {
                for (int j = 0; j < nbOccurences; j++) {
                    if (traiteSaisi[i] != -1 && traiteATrouver[j] != -1) {
                        if (valeurSaisi[i] == valeurATrouver[j]) {
                            present = present + 1;
                            traiteSaisi[i] = -1;
                            traiteATrouver[j] = -1;
                        }
                    }
                }
            }
        }

        return bienPlace;
    }

    /**
     * Affichage du résultat de la comparaison entre la proposition et la combinaison à trouver.
     * @param mode pour gérer l'affichage du resultat de comparaison HUMAIN = proposition du joueur, ORDINATEUR = proposition de l'ordinateur.
     */
    private void afficheResultat(boolean mode, int[] valeur){
        String resultat = "aucune correspondance";

        if (present > 0) {
            resultat = present + " present";
        }
        if (present > 1) {
            resultat = resultat + "s";
        }
        if (present > 0 && bienPlace > 0) {
            resultat = resultat + ", " + bienPlace + " bien placé";
        } else if (bienPlace > 0) {
            resultat = bienPlace + " bien placé";
        }
        if (bienPlace > 1) {
            resultat = resultat + "s";
        }

        if (mode == HUMAIN) {
            System.out.println("Votre proposition ...........: " + Arrays.toString(valeur).replace(", ", "") + " -> Réponse " + resultat);
        }
        else {
            System.out.println("Proposition de l'ordinateur .: " + Arrays.toString(valeur).replace(", ", "") + " -> Réponse " + resultat);
        }

    }


    /**
     * Proposition de l'ordianteur.
     *
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
     *
     * @param valModeJeu choix du mode de jeu.
     * @return rapport compte rendu du r&eacute;sultat de la partie.
     */
    public String lanceMastermind(String valModeJeu) {
        String rapport = "ToDo";
        int nbrCoups = 0;
        int resultat = 0;
        int resultat2 = 0; // Pour la comparaison de la proposition de l'ordinateur
        int[] nombreATrouver = new int[Main.NB_DIGIT_MASTERMIND]; // Resultat du tirage aléatoire ou de la proposition humaine
        int[] propositionPrecedente =  new int[Main.NB_DIGIT_MASTERMIND]; // Permet de conserver la trace de la proposition précédente de l'ordinateur
        String solution;

        Outils outils = new Outils();

        switch (valModeJeu) {
            case "1": // Mode Challenger
                nombreATrouver = outils.tirageDuNombre(Main.NB_DIGIT_MASTERMIND, Main.NB_VALEURS_MASTERMIND, Main.DEBUG);

                do {
                    int[] nombreSaisi = outils.saisieNombre(Main.NB_DIGIT_MASTERMIND, Main.NB_VALEURS_MASTERMIND, PROPOSITION, Main.DEBUG);
                    resultat = compareSaisie(nombreATrouver, nombreSaisi, Main.NB_DIGIT_MASTERMIND);
                    afficheResultat(HUMAIN, nombreSaisi);
                    nbrCoups++;
                }
                while ((resultat != Main.NB_DIGIT_MASTERMIND) && (nbrCoups < Main.ESSAIS_MAX_MASTERMIND));
                if (resultat == Main.NB_DIGIT_MASTERMIND){
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