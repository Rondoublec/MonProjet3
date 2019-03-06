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
     */
    public void lancePlusMoins(String valModeJeu) {
        String solution = "";
        int nbrCoups = 0;
        String resultat = "";
        String resultat2 = "";
        boolean vainqueur = false;
        boolean vainqueur2 = false;
        int[] nombreATrouver = new int[Main.NB_DIGIT_PLUS_MOINS]; // Valeur du tirage aléatoire
        int[] nombreATrouverParOrdinateur = new int[Main.NB_DIGIT_PLUS_MOINS]; // Valeur de la proposition humaine
        int[] propositionPrecedente = new int[Main.NB_DIGIT_PLUS_MOINS]; // Permet de conserver la trace de la proposition précédente de l'ordinateur
        int[] nombreSaisi =  new int[Main.NB_DIGIT_PLUS_MOINS]; // Saisie de la proposition de l'utilisateur
        int[] nombrePropose =  new int[Main.NB_DIGIT_PLUS_MOINS]; // Proposition de l'ordinateur

        Outils outils = new Outils();
        Result result = new Result();

        logger.info("lancePlusMoins en mode " + valModeJeu);

        Main.GAGNE = outils.fabriqueChaine("=", Main.NB_DIGIT_PLUS_MOINS);
        logger.debug("Main.NB_DIGIT : " + Main.NB_DIGIT_PLUS_MOINS + " Main.GAGNE : " + Main.GAGNE);

        switch (valModeJeu) {
            case "1": // Mode Challenger
                nombreATrouver = outils.tirageDuNombre(Main.NB_DIGIT_PLUS_MOINS, Main.NB_VALEURS_PLUS_MOINS , Main.DEBUG);

                do {
                    nombreSaisi = outils.saisieNombre(Main.NB_DIGIT_PLUS_MOINS, Main.NB_VALEURS_PLUS_MOINS, PROPOSITION, Main.DEBUG);
                    resultat = compareSaisie(nombreATrouver, nombreSaisi, Main.NB_DIGIT_PLUS_MOINS);
                    result.afficheReponse(HUMAIN, 0,0, nombreSaisi, resultat);
                    nbrCoups++;
                }
                while (!resultat.equals(Main.GAGNE) && nbrCoups < Main.ESSAIS_MAX_PLUS_MOINS);
                solution = Arrays.toString(nombreATrouver).replace(", ", "");
                if (resultat.equals(Main.GAGNE)){
                    vainqueur = HUMAIN;
                    vainqueur2 = HUMAIN;
                } else {
                    vainqueur = ORDINATEUR;
                    vainqueur2 = ORDINATEUR;
                }
                break;
            case "2": // Mode défenseur
                nombreATrouverParOrdinateur = outils.saisieNombre(Main.NB_DIGIT_PLUS_MOINS, Main.NB_VALEURS_PLUS_MOINS, SECRET, Main.DEBUG);
                resultat2 = Main.GAGNE;

                do {
                    nombrePropose = propositionOrdinateur (propositionPrecedente, resultat2);
                    resultat2 = compareSaisie(nombreATrouverParOrdinateur, nombrePropose,Main.NB_DIGIT_PLUS_MOINS);
                    result.afficheReponse(ORDINATEUR, 0,0, nombrePropose, resultat2);
                    propositionPrecedente = nombrePropose;
                    nbrCoups++;
                }
                while (!resultat2.equals(Main.GAGNE) && nbrCoups < Main.ESSAIS_MAX_PLUS_MOINS);
                solution = Arrays.toString(nombreATrouverParOrdinateur).replace(", ", "");
                if (resultat2.equals(Main.GAGNE)){
                    vainqueur = ORDINATEUR;
                    vainqueur2 = ORDINATEUR;
                } else {
                    vainqueur = HUMAIN;
                    vainqueur2 = HUMAIN;
                }
                break;
            case "3": // Mode Duel
                nombreATrouver = outils.tirageDuNombre(Main.NB_DIGIT_PLUS_MOINS, Main.NB_VALEURS_PLUS_MOINS, Main.DEBUG);
                nombreATrouverParOrdinateur = outils.saisieNombre(Main.NB_DIGIT_PLUS_MOINS, Main.NB_VALEURS_PLUS_MOINS, SECRET, Main.DEBUG);
                resultat2 = Main.GAGNE;

                do {
                    nombreSaisi = outils.saisieNombre(Main.NB_DIGIT_PLUS_MOINS, Main.NB_VALEURS_PLUS_MOINS, PROPOSITION, Main.DEBUG);
                    resultat = compareSaisie(nombreATrouver, nombreSaisi,Main.NB_DIGIT_PLUS_MOINS);
                    result.afficheReponse(HUMAIN, 0,0, nombreSaisi, resultat);
                    nombrePropose = propositionOrdinateur (propositionPrecedente, resultat2);
                    resultat2 = compareSaisie(nombreATrouverParOrdinateur, nombrePropose, Main.NB_DIGIT_PLUS_MOINS);
                    result.afficheReponse(ORDINATEUR, 0,0, nombrePropose, resultat2);

                    propositionPrecedente = nombrePropose;
                    nbrCoups++;
                }
                while (!resultat.equals(Main.GAGNE) && !resultat2.equals(Main.GAGNE) );
                solution = Arrays.toString(nombreATrouver).replace(", ", "");
                if ((resultat.equals(Main.GAGNE)) && (resultat2.equals(Main.GAGNE))) {
                    vainqueur = HUMAIN;
                    vainqueur2 = ORDINATEUR;
                } else if (resultat.equals(Main.GAGNE)){
                    vainqueur = HUMAIN;
                    vainqueur2 = HUMAIN;
                } else {
                    vainqueur = ORDINATEUR;
                    vainqueur2 = ORDINATEUR;
                }
                break;
            default:
                System.err.println("Mode de jeu [" + valModeJeu + "] inconnu ... ERREUR valeur incorrecte !");
                logger.error("Mode de jeu [" + valModeJeu + "] inconnu ... ERREUR valeur incorrecte !");
                break;
        }
        result.afficheRapport("1",valModeJeu,vainqueur,vainqueur2,nbrCoups,solution);
    }
}