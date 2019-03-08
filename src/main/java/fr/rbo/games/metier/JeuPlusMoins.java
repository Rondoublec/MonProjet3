package fr.rbo.games.metier;

import fr.rbo.games.Main;
import fr.rbo.games.ihm.Result;
import fr.rbo.games.ihm.TypeMenu;
import fr.rbo.games.ihm.TypeSousMenu;
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
public class JeuPlusMoins extends Jeu{

    private static final Logger logger = LogManager.getLogger();

    private int nbLignes;
    private int nbCases;
    private int nbValeurs;

    public JeuPlusMoins(String nomDuJeu, String descriptionDuJeu, int nbLignes, int nbCases, int nbValeurs) {
        super(nomDuJeu, descriptionDuJeu);
        this.nbLignes = nbLignes;
        this.nbCases = nbCases;
        this.nbValeurs = nbValeurs;
    }

    /**
     * Comparaison de la s&eacute;rie &agrave; trouver et de la s&eacute;rie propos&eacute;e par le joueur.
     *
     * @param nombreATrouver s&eacute;rie &agrave; trouver.
     * @param nombreSaisi s&eacute;rie propos&eacute;e par le joueur.
     * @param nbOccurences nombre de caractères / digits dont est constituée la chaine.
     * @return resultat = Tableau de NB_DIGIT &eacute;l&eacute;ments de la comparaison chiffre &agrave; chiffre, valeurs possibles =, -, +.
     */
    String compareSaisie(int[] nombreATrouver, int[] nombreSaisi, int nbOccurences) {
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
    private int[] propositionOrdinateur(int[] propositionPrecedente, String resultat){
        int[] nombrePropose = new int[nbCases];

        for (int i=0; i < nbCases; i++) {
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
    public void lancePlusMoins(TypeSousMenu valModeJeu) {
        String solution = "";
        int nbrCoups = 0;
        String resultatHumain = "";
        String resultatOrdinateur = "";
        int[] nombreATrouver = new int[nbCases]; // Valeur du tirage aléatoire
        int[] nombreATrouverParOrdinateur = new int[nbCases]; // Valeur de la proposition humaine
        int[] propositionPrecedente = new int[nbCases]; // Permet de conserver la trace de la proposition précédente de l'ordinateur
        int[] nombreSaisi =  new int[nbCases]; // Saisie de la proposition de l'utilisateur
        int[] nombrePropose =  new int[nbCases]; // Proposition de l'ordinateur

        Outils outils = new Outils();
        Result result = new Result();

        logger.info("lancePlusMoins en mode " + valModeJeu);

        Main.GAGNE = outils.fabriqueChaine("=", nbCases);
        logger.debug("nbCases : " + nbCases + " Main.GAGNE : " + Main.GAGNE);

        switch (valModeJeu) {
            case CHALLENGER: // Mode Challenger
                nombreATrouver = outils.tirageDuNombre(nbCases, nbValeurs, Main.DEBUG);

                do {
                    nombreSaisi = outils.saisieNombre(nbCases, nbValeurs, PROPOSITION, Main.DEBUG);
                    resultatHumain = compareSaisie(nombreATrouver, nombreSaisi, nbCases);
                    result.afficheReponse(HUMAIN, 0,0, nombreSaisi, resultatHumain);
                    nbrCoups++;
                }
                while (!resultatHumain.equals(Main.GAGNE) && nbrCoups < nbLignes);
                solution = Arrays.toString(nombreATrouver).replace(", ", "");
                result.afficheRapport(TypeMenu.PLUS_MOINS,valModeJeu,resultatHumain.equals(Main.GAGNE),resultatHumain.equals(Main.GAGNE),nbrCoups,solution);
                break;
            case DEFENSEUR: // Mode défenseur
                nombreATrouverParOrdinateur = outils.saisieNombre(nbCases, nbValeurs, SECRET, Main.DEBUG);
                resultatOrdinateur = Main.GAGNE;

                do {
                    nombrePropose = propositionOrdinateur (propositionPrecedente, resultatOrdinateur);
                    resultatOrdinateur = compareSaisie(nombreATrouverParOrdinateur, nombrePropose,nbCases);
                    result.afficheReponse(ORDINATEUR, 0,0, nombrePropose, resultatOrdinateur);
                    propositionPrecedente = nombrePropose;
                    nbrCoups++;
                }
                while (!resultatOrdinateur.equals(Main.GAGNE) && nbrCoups < nbLignes);
                solution = Arrays.toString(nombreATrouverParOrdinateur).replace(", ", "");
                result.afficheRapport(TypeMenu.PLUS_MOINS,valModeJeu,resultatOrdinateur.equals(Main.GAGNE),resultatOrdinateur.equals(Main.GAGNE),nbrCoups,solution);
                break;
            case DUEL: // Mode Duel
                nombreATrouver = outils.tirageDuNombre(nbCases, nbValeurs, Main.DEBUG);
                nombreATrouverParOrdinateur = outils.saisieNombre(nbCases, nbValeurs, SECRET, Main.DEBUG);
                resultatOrdinateur = Main.GAGNE;

                do {
                    nombreSaisi = outils.saisieNombre(nbCases, nbValeurs, PROPOSITION, Main.DEBUG);
                    resultatHumain = compareSaisie(nombreATrouver, nombreSaisi,nbCases);
                    result.afficheReponse(HUMAIN, 0,0, nombreSaisi, resultatHumain);
                    nombrePropose = propositionOrdinateur (propositionPrecedente, resultatOrdinateur);
                    resultatOrdinateur = compareSaisie(nombreATrouverParOrdinateur, nombrePropose, nbCases);
                    result.afficheReponse(ORDINATEUR, 0,0, nombrePropose, resultatOrdinateur);

                    propositionPrecedente = nombrePropose;
                    nbrCoups++;
                }
                while (!resultatHumain.equals(Main.GAGNE) && !resultatOrdinateur.equals(Main.GAGNE) );
                solution = Arrays.toString(nombreATrouver).replace(", ", "");
                result.afficheRapport(TypeMenu.PLUS_MOINS,valModeJeu,resultatHumain.equals(Main.GAGNE),resultatOrdinateur.equals(Main.GAGNE),nbrCoups,solution);
                break;
            default:
                System.err.println("Mode de jeu [" + valModeJeu + "] inconnu ... ERREUR valeur incorrecte !");
                logger.error("Mode de jeu [" + valModeJeu + "] inconnu ... ERREUR valeur incorrecte !");
                break;
        }
    }
}