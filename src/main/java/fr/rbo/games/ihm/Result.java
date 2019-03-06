package fr.rbo.games.ihm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Affiche les r&eacute;sultats des jeux et de la partie.
 * @author R&eacute;my Bourdoncle
 * @version 0.1
 */

public class Result {

    private static final boolean PROPOSITION = true;
    private static final boolean SECRET = false;
    private static final boolean HUMAIN = true;
    private static final boolean ORDINATEUR = false;

    private static final Logger logger = LogManager.getLogger();

    /**
     * Affichage du r&eacute;sultat de la comparaison entre la proposition et la combinaison &agrave; trouver.
     * @param mode pour g&eacute;rer l'affichage du resultat de comparaison HUMAIN = proposition du joueur, ORDINATEUR = proposition de l'ordinateur.
     * @param present nombre de valeurs pr&eacute;sent (mais mal plac&eacute;es)
     * @param bienPlace nombre de valeurs bien plac&eacute;es
     * @param valeur proposition &eacute;valu&eacute;e
     * @param resultat pour g&eacute;rer l'affichage du resultat de comparaison.
     */
    public void afficheReponse(boolean mode, int bienPlace, int present, int[] valeur, String resultat){

        if (resultat.equals("")) { // Si la chaine est vide, il faut la fabriquer, sinon on l'affiche directement.
            if (present == 0 && bienPlace == 0){
                resultat = "pas de correspondance";
            }
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
        }

        if (mode == HUMAIN) {
            System.out.println("Votre proposition ...........: " + Arrays.toString(valeur).replace(", ", "") + " -> Réponse " + resultat);
        }
        else {
            System.out.println("Proposition de l'ordinateur .: " + Arrays.toString(valeur).replace(", ", "") + " -> Réponse " + resultat);
        }
    }

    /**
     * Affichage du rapport de fin de partie
     * @param jeu valeur du jeu choisi
     * @param mode valeur du mode de jeu choisi
     * @param vainqueur HUMAIN (true) ORDINATEUR (false)
     * @param vainqueur2 HUMAIN (true) ORDINATEUR (false)
     * @param nbrCoups nombre de tentatives jou&eacute;es
     * @param solution combinaison secr&egrave;te &agrave; trouver
     */
    public void afficheRapport(String jeu, String mode, boolean vainqueur, boolean vainqueur2, int nbrCoups, String solution){
        String rapport = "FIN";

        switch (jeu){
            case "1": // Recherche Plus ou Moins
                rapport = "\n \\o/ Résultat du jeu de recherche Plus ou moins : ";
                break;
            case "2": // Mastermind
                rapport = "\n \\o/ Résultat du jeu de Mastermind : ";
                break;
            default:
                System.err.println("Jeu ["+ jeu + "] inconnu ... Fin anormale");
                logger.error("Jeu ["+ jeu + "] inconnu ... Fin anormale");
                System.exit(9);
                break;
        }
        switch (mode) {
            case "1": // Challenger
                if (vainqueur == HUMAIN && vainqueur2 == HUMAIN) {
                    rapport = "Gagné en " + nbrCoups + " coups. La solution est : ";
                } else {
                    rapport = "Perdu, nombre de tentatives (" + nbrCoups + ") atteint. La solution est : ";
                }
                rapport = rapport + solution + ".";
                break;
            case "2": // Defenseur
                if (vainqueur == ORDINATEUR && vainqueur2 == ORDINATEUR){
                    rapport = "L'ordinateur gagne, il a trouvé en " + nbrCoups;
                } else {
                    rapport = "Vous gagnez, l'ordinateur n'a pas trouvé en " + nbrCoups;
                }
                rapport = rapport + " coups votre combinaison secrete : " + solution + ".";
                break;
            case "3": // Duel
                if ((vainqueur == HUMAIN) && (vainqueur2 == ORDINATEUR)) {
                    rapport = "Egalité en " + nbrCoups + " coups." ;
                } else if (vainqueur == HUMAIN){
                    rapport = "Humain gagne en " + nbrCoups + " coups.";;
                } else {
                    rapport = "Ordinateur gagne en " + nbrCoups + " coups. La solution à trouver était : " + solution +".";;
                }
                break;
            default:
                System.err.println("Mode de jeu [" + mode + "] inconnu ... Fin anormale");
                logger.error("Mode de jeu [" + mode + "] inconnu ... Fin anormale");
                System.exit(9);
                break;
        }
        System.out.println(rapport + "\n");
    }
}
