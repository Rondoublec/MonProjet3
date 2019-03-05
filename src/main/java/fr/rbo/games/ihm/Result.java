package fr.rbo.games.ihm;

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

    /**
     * Affichage du r&eacute;sultat de la comparaison entre la proposition et la combinaison &agrave; trouver.
     * @param mode pour g&eacute;rer l'affichage du resultat de comparaison HUMAIN = proposition du joueur, ORDINATEUR = proposition de l'ordinateur.
     * @param present nombre de valeurs pr&eacute;sent (mais mal plac&eacute;es)
     * @param bienPlace nombre de valeurs bien plac&eacute;es
     * @param valeur proposition &eacute;valu&eacute;e
     * @param resultat pour gérer l'affichage du resultat de comparaison.
     */
    public void afficheResultat(boolean mode, int bienPlace, int present, int[] valeur, String resultat){

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

}
