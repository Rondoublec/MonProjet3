package fr.rbo.games.metier;

import fr.rbo.games.Main;
import fr.rbo.games.ihm.TypeMenu;
import fr.rbo.games.ihm.TypeSousMenu;
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

public class JeuMastermind extends Jeu{

    private static final Logger logger = LogManager.getLogger();

    private List<String> listeCombinaisons = new ArrayList<>();
    private List<String> listePropositions = new ArrayList<>();
    private List<Integer> listeDesScores = new ArrayList<>();

    private int nbLignes;
    private int nbCases;
    private int nbValeurs;

    public JeuMastermind(String nomDuJeu, String descriptionDuJeu, int nbLignes, int nbCases, int nbValeurs) {
        super(nomDuJeu, descriptionDuJeu);
        this.nbLignes = nbLignes;
        this.nbCases = nbCases;
        this.nbValeurs = nbValeurs;
    }

    /**
     * Cr&eacute;ation de la liste des combinaisons possible, cette liste permettra par &eacute;limination de d&eacute;couvrir les combinaisons possibles
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
    int score(int[] valeurATrouver, int[] valeurProposee, int nbCases, int nbCouleurs) {

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
    private int[] rechercheProposition(int nbCases, int nbCouleurs){
        int[] propositionAEvaluer = new int[nbCases];
        int[] propositionEvalueeHisto =  new int[nbCases];
        boolean scoreOK;
        int indiceListeDesPropositionsPossibles = 0;
        int indiceHistoriqueDesPropositionsTestees = 0;
        int scorePropositionATrouver = 0;
        double nbMax = 0;

        if (nbCouleurs <= 10 && nbCases <= 8) { // protection contre le dépassement de capacité
            nbMax = Math.pow(nbCouleurs, nbCases);
        }

        while ( indiceListeDesPropositionsPossibles < nbMax){
            // récupération des combinaisons de la liste des possibilitées
            for (int j = 0;j<nbCases;j++){
                propositionAEvaluer[j] = (int) listeCombinaisons.get(indiceListeDesPropositionsPossibles).charAt(j) - 48;
            }
            scoreOK = true;
            indiceHistoriqueDesPropositionsTestees = 0;
            while ((indiceHistoriqueDesPropositionsTestees < listePropositions.size()) && (scoreOK)){
                for (int j = 0;j<nbCases;j++){
                    propositionEvalueeHisto[j] = (int) listePropositions.get(indiceHistoriqueDesPropositionsTestees).charAt(j) - 48;
                }
                scorePropositionATrouver = score(propositionAEvaluer, propositionEvalueeHisto, nbCases, nbValeurs);
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
     */
    public void lanceMastermind(TypeSousMenu valModeJeu) {
        String solution = "";
        int nbrCoups = 0;
        int resultatHumain = 0;
        int resultatOrdinateur = 0; // Pour la comparaison de la proposition de l'ordinateur
        int rougeHumain, blancHumain = 0; // r = nombre de rouges (=biens placés), b = nombre de blancs (=présents)
        int rougeOrdinateur, blancOrdinateur = 0; // r2 = nombre de rouges (=biens placés), b2 = nombre de blancs (=présents) pour l'ordinateur
        int[] nombreATrouver = new int[nbCases]; // Resultat du tirage aléatoire ou de la proposition humaine
        int[] nombreATrouverParOrdinateur = new int[nbCases]; // Valeur de la proposition humaine
        int[] nombreSaisi =  new int[nbCases]; // Saisie de la proposition de l'utilisateur
        int[] nombrePropose =  new int[nbCases]; // Proposition de l'ordinateur

        Outils outils = new Outils();
        Result result = new Result();

        logger.info("lanceMastermind en mode " + valModeJeu);

        switch (valModeJeu) {
            case CHALLENGER: // Mode Challenger
                nombreATrouver = outils.tirageDuNombre(nbCases, nbValeurs, Main.DEBUG);
                do {
                    nombreSaisi = outils.saisieNombre(nbCases, nbValeurs, PROPOSITION, Main.DEBUG);
                    resultatHumain = score(nombreATrouver, nombreSaisi, nbCases, nbValeurs);
                    rougeHumain = resultatHumain/10;
                    blancHumain = resultatHumain%10;
                    result.afficheReponse(HUMAIN, rougeHumain, blancHumain , nombreSaisi, "");
                    nbrCoups++;
                }
                while ((rougeHumain != nbCases) && (nbrCoups < nbLignes));
                solution = Arrays.toString(nombreATrouver).replace(", ", "");
                result.afficheRapport(TypeMenu.MASTERMIND,valModeJeu,(rougeHumain == nbCases),(rougeHumain == nbCases),nbrCoups,solution);
                break;

            case DEFENSEUR: // Mode défenseur
                creationListeDesCombinaisons(nbCases, nbValeurs);
                nombreATrouverParOrdinateur = outils.saisieNombre(nbCases, nbValeurs, SECRET, Main.DEBUG);

                do {
                    if (nbrCoups == 0){ // au 1er passage on tire un nombre aléatoire pour commencer à chercher
                        nombrePropose = outils.tirageDuNombre(nbCases, nbValeurs,ORDINATEUR);
                    } else {
                        nombrePropose = rechercheProposition(nbCases, nbValeurs);
                    }
                    String cList = "";
                    for (int i = 0; i < nbCases; i++){
                        cList = cList + nombrePropose[i];
                    }
                    listePropositions.add(cList); // Sauvegarde de la proposition
                    resultatOrdinateur = score(nombreATrouverParOrdinateur, nombrePropose, nbCases, nbValeurs);
                    listeDesScores.add(resultatOrdinateur);
                    logger.debug("         Proposition N° " + nbrCoups + " : " + listePropositions.get(nbrCoups) + " score : " + listeDesScores.get(nbrCoups));
                    rougeOrdinateur = resultatOrdinateur/10;
                    blancOrdinateur = resultatOrdinateur%10;
                    result.afficheReponse(ORDINATEUR, rougeOrdinateur, blancOrdinateur , nombrePropose, "");
                    nbrCoups++;
                }
                while ((rougeOrdinateur != nbCases) && (nbrCoups < nbLignes));
                solution = Arrays.toString(nombreATrouverParOrdinateur).replace(", ", "");
                result.afficheRapport(TypeMenu.MASTERMIND,valModeJeu,(rougeOrdinateur == nbCases),(rougeOrdinateur == nbCases),nbrCoups,solution);
                break;

            case DUEL: // Mode Duel
                nombreATrouver = outils.tirageDuNombre(nbCases, nbValeurs, Main.DEBUG);
                creationListeDesCombinaisons(nbCases, nbValeurs);
                nombreATrouverParOrdinateur = outils.saisieNombre(nbCases, nbValeurs, SECRET, Main.DEBUG);
                do {
                    // HUMAIN ==================== JOUE
                    nombreSaisi = outils.saisieNombre(nbCases, nbValeurs, PROPOSITION, Main.DEBUG);
                    resultatHumain = score(nombreATrouver, nombreSaisi, nbCases, nbValeurs);
                    rougeHumain = resultatHumain/10;
                    blancHumain = resultatHumain%10;
                    result.afficheReponse(HUMAIN, rougeHumain, blancHumain , nombreSaisi, "");
                    // ORDINATEUR ================ JOUE
                    if (nbrCoups == 0){ // au 1er passage on tire un nombre aléatoire pour commencer à chercher
                        nombrePropose = outils.tirageDuNombre(nbCases, nbValeurs, ORDINATEUR);
                    } else {
                        nombrePropose = rechercheProposition(nbCases, nbValeurs);
                    }
                    String cList = "";
                    for (int i = 0; i < nbCases; i++){
                        cList = cList + nombrePropose[i];
                    }
                    listePropositions.add(cList); // Sauvegarde de la proposition
                    resultatOrdinateur = score(nombreATrouverParOrdinateur, nombrePropose, nbCases, nbValeurs);
                    listeDesScores.add(resultatOrdinateur);
                    logger.debug("         Proposition N° " + nbrCoups + " : " + listePropositions.get(nbrCoups) + " score : " + listeDesScores.get(nbrCoups));
                    rougeOrdinateur = resultatOrdinateur/10;
                    blancOrdinateur = resultatOrdinateur%10;
                    result.afficheReponse(ORDINATEUR, rougeOrdinateur, blancOrdinateur , nombrePropose, "");
                    nbrCoups++;
                }
                while ((rougeHumain != nbCases)&&(rougeOrdinateur != nbCases));
                solution = Arrays.toString(nombreATrouver).replace(", ", "");
                result.afficheRapport(TypeMenu.MASTERMIND,valModeJeu,(rougeHumain == nbCases),(rougeOrdinateur == nbCases),nbrCoups,solution);
                break;

            default:
                System.err.println("Mode de jeu [" + valModeJeu + "] inconnu ... ERREUR valeur incorrecte !");
                logger.error("Mode de jeu [" + valModeJeu + "] inconnu ... ERREUR valeur incorrecte !");
                break;
        }
    }
}