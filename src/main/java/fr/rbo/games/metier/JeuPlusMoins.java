package fr.rbo.games.metier;

import fr.rbo.games.Main;
import fr.rbo.games.ihm.Result;
import fr.rbo.games.util.Outils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Jeu de recherche plus ou moins
 * et ses 3 modes de jeu Challenger, D&eacute;fenseur, Duel.
 * @author R&eacute;my Bourdoncle
 * @version 0.1
 */
public class JeuPlusMoins {

    private static final boolean PROPOSITION = true;
    private static final boolean SECRET = false;
    private static final boolean HUMAIN = true;
    private static final boolean ORDINATEUR = false;

    private static final Logger logger = LogManager.getLogger();

    /**
     * Comparaison de la s&eacute;rie &agrave; trouver et de la s&eacute;rie propos&eacute;e par le joueur.
     *
     * @param nombreATrouver s&eacute;rie &agrave; trouver.
     * @param nombreSaisi s&eacute;rie propos&eacute;e par le joueur.
     * @param nbOccurences nombre de caractères / digits dont est constituée la chaine.
     * @return resultat = Tableau de NB_DIGIT &eacute;l&eacute;ments de la comparaison chiffre &agrave; chiffre, valeurs possibles =, -, +.
     */
    public String compareSaisie(int[] nombreATrouver, int[] nombreSaisi, int nbOccurences) {
        String resultat = "";

        logger.debug("Solution = " + Arrays.toString(nombreATrouver).replace(", ", ""));
        for (int i=0; i < nbOccurences; i++) {
            if (nombreSaisi[i] == nombreATrouver[i]) {
                resultat = resultat + "=";
            } else if (nombreSaisi[i] < nombreATrouver[i]) {
                resultat = resultat + "+";
            } else {
                resultat = resultat + "-";
            }
        }

        return resultat;
    }

    /**
     * Proposition de l'ordianteur.
     *
     * @param propositionPrecedente valeur de la proposition pr&eacute;c&eacute;dente.
     * @param resultat valeur de la comparaison pr&eacute;c&eacute;dente.
     * @return nombrePropose = Tableau de NB_DIGIT &eacute;l&eacute;ments num&eacute;riques, valeurs possibles 0 &agrave; 9.
     */
    protected int[] propositionOrdinateur(int[] propositionPrecedente, String resultat){
        int[] nombrePropose = new int[Main.NB_DIGIT_PLUS_MOINS];

        for (int i=0; i < Main.NB_DIGIT_PLUS_MOINS; i++) {
            if (resultat.charAt(i) == '=') {
                nombrePropose[i] = propositionPrecedente[i];
            } else if (resultat.charAt(i) == '+') {
                if (propositionPrecedente[i] < 8) {
                    nombrePropose[i] = propositionPrecedente[i] + 2;
                } else {
                    nombrePropose[i] = propositionPrecedente[i] + 1;
                }
            } else nombrePropose[i] = propositionPrecedente[i] - 1;
        }
        logger.debug("NombrePropose par l'ordinateur : " + Arrays.toString(nombrePropose).replace(", ", ""));

        return nombrePropose;
    }


    /**
     * Lancement du jeu Plus ou Moins.
     *
     * @param valModeJeu choix du mode de jeu.
     * @return rapport = compte rendu du r&eacute;sultat de la partie.
     */
    public String lancePlusMoins(String valModeJeu) {
        String rapport = "ToDo";
        int nbrCoups = 0;
        String resultat = "";
        String resultat2 = "";
        int[] nombreATrouver = new int[Main.NB_DIGIT_PLUS_MOINS]; // Valeur du tirage aléatoire
        int[] nombreATrouverParOrdinateur = new int[Main.NB_DIGIT_PLUS_MOINS]; // Valeur de la proposition humaine
        int[] propositionPrecedente = new int[Main.NB_DIGIT_PLUS_MOINS]; // Permet de conserver la trace de la proposition précédente de l'ordinateur
        int[] nombreSaisi =  new int[Main.NB_DIGIT_PLUS_MOINS]; // Permet
        int[] nombrePropose =  new int[Main.NB_DIGIT_PLUS_MOINS]; // Permt

        Outils outils = new Outils();
        Result result = new Result();

        Main.GAGNE = outils.fabriqueChaine("=", Main.NB_DIGIT_PLUS_MOINS);
        logger.debug("Main.NB_DIGIT : " + Main.NB_DIGIT_PLUS_MOINS + " Main.GAGNE : " + Main.GAGNE);

        switch (valModeJeu) {
            case "1": // Mode Challenger
                nombreATrouver = outils.tirageDuNombre(Main.NB_DIGIT_PLUS_MOINS, Main.NB_VALEURS_PLUS_MOINS , Main.DEBUG);

                do {
                    nombreSaisi = outils.saisieNombre(Main.NB_DIGIT_PLUS_MOINS, Main.NB_VALEURS_PLUS_MOINS, PROPOSITION, Main.DEBUG);
                    resultat = compareSaisie(nombreATrouver, nombreSaisi, Main.NB_DIGIT_PLUS_MOINS);
                    result.afficheResultat(HUMAIN, 0,0, nombreSaisi, resultat);
                    nbrCoups++;
                }
                while (!resultat.equals(Main.GAGNE) && nbrCoups < Main.ESSAIS_MAX_PLUS_MOINS);
                if (resultat.equals(Main.GAGNE)){
                    rapport = "Gagné en " + nbrCoups + " coups. Solution ";
                } else {
                    rapport = "Perdu, nombre de tentatives (" + nbrCoups + ") atteint, la solution est : ";
                }
                rapport = rapport + Arrays.toString(nombreATrouver).replace(", ", "") + ".";
                break;
            case "2": // Mode défenseur
                nombreATrouverParOrdinateur = outils.saisieNombre(Main.NB_DIGIT_PLUS_MOINS, Main.NB_VALEURS_PLUS_MOINS, SECRET, Main.DEBUG);
                resultat2 = Main.GAGNE;

                do {
                    nombrePropose = propositionOrdinateur (propositionPrecedente, resultat2);
                    resultat2 = compareSaisie(nombreATrouverParOrdinateur, nombrePropose,Main.NB_DIGIT_PLUS_MOINS);
                    result.afficheResultat(ORDINATEUR, 0,0, nombrePropose, resultat2);
                    propositionPrecedente = nombrePropose;
                    nbrCoups++;
                }
                while (!resultat2.equals(Main.GAGNE) && nbrCoups < Main.ESSAIS_MAX_PLUS_MOINS);
                rapport = "Votre combinaison secrete : " + Arrays.toString(nombreATrouverParOrdinateur).replace(", ", "");
                if (resultat2.equals(Main.GAGNE)){
                    rapport = "L'ordinateur gagne, il a trouvé en " + nbrCoups;
                } else {
                    rapport = "Vous gagnez, l'ordinateur n'a pas trouvé en " + nbrCoups;
                }
                rapport = rapport + " coups votre combinaison secrete : " + Arrays.toString(nombreATrouverParOrdinateur).replace(", ", "");
                break;
            case "3": // Mode Duel
                nombreATrouver = outils.tirageDuNombre(Main.NB_DIGIT_PLUS_MOINS, Main.NB_VALEURS_PLUS_MOINS, Main.DEBUG);
                nombreATrouverParOrdinateur = outils.saisieNombre(Main.NB_DIGIT_PLUS_MOINS, Main.NB_VALEURS_PLUS_MOINS, SECRET, Main.DEBUG);
                resultat2 = Main.GAGNE;

                do {
                    nombreSaisi = outils.saisieNombre(Main.NB_DIGIT_PLUS_MOINS, Main.NB_VALEURS_PLUS_MOINS, PROPOSITION, Main.DEBUG);
                    resultat = compareSaisie(nombreATrouver, nombreSaisi,Main.NB_DIGIT_PLUS_MOINS);
                    result.afficheResultat(HUMAIN, 0,0, nombreSaisi, resultat);
                    nombrePropose = propositionOrdinateur (propositionPrecedente, resultat2);
                    resultat2 = compareSaisie(nombreATrouverParOrdinateur, nombrePropose, Main.NB_DIGIT_PLUS_MOINS);
                    result.afficheResultat(ORDINATEUR, 0,0, nombrePropose, resultat2);

                    propositionPrecedente = nombrePropose;
                    nbrCoups++;
                }
                while (!resultat.equals(Main.GAGNE) && !resultat2.equals(Main.GAGNE) );

                if ((resultat.equals(Main.GAGNE)) && (resultat2.equals(Main.GAGNE))) {
                    rapport = "Egalité en ";
                } else if (resultat.equals(Main.GAGNE)){
                    rapport = "Humain gagne en ";
                } else {
                    rapport = "Ordinateur gagne en ";
                }
                rapport = rapport + nbrCoups + " coups. Solution " + Arrays.toString(nombreATrouver).replace(", ", "") +".";
                break;
            default:
                System.out.println("ERREUR valeur incorrecte !");
                break;
        }
        return rapport;
    }
}