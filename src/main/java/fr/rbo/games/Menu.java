package fr.rbo.games;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

/**
 * Gestion des menus.
 * @author R&eacute;my Bourdoncle
 * @version 0.1
 */
public class Menu {

    private static Scanner sc = new Scanner(System.in);
    private static String choixJeu = "";
    private static String choixMode = "";
    private static final Logger logger = LogManager.getLogger();

    /**
     * Choix de jeu
     * @return choixJeu (9 si fin demand&eacute;e).
     */
    public static String getChoixJeu() {
        return choixJeu;
    }

    /**
     * Mode de jeu
     * @return choixMode (9 si fin demand&eacute;e).
     */
    public static String getChoixMode() {
        return choixMode;
    }

    /**
     * Affiche les jeux disponbles.
     */
    private void afficheMenuChoixJeu() {
        choixJeu = "";
        boolean choixValide = false;

        do {
            System.out.println("Choix du jeu");
            System.out.println("1 - Plus ou Moins");
            System.out.println("2 - Mastermind");
            System.out.println("Faites votre choix ou 9 pour terminer.");
            choixJeu = sc.nextLine();
            if ((!choixJeu.equals("1")) && (!choixJeu.equals("2")) && (!choixJeu.equals("9"))) {
                System.out.println("Choix invalide - recommencez ! \n");
            } else {
                choixValide = true;
            }
        } while ( !choixValide );
    }

    /**
     * Affiche le choix effectu&eacute;.
     */
    private void afficheJeuChoisi() {
        switch (choixJeu) {
            case "1":
                System.out.println("Vous avez choisi le jeu Plus ou Moins\n");
                break;
            case "2":
                System.out.println("Vous avez choisi le jeu Mastermind\n");
                break;
            case "9":
                System.out.println("Merci et à bientôt\n");
                break;
            default:
                System.out.println("ERREUR valeur incorrecte\n");
                break;
        }
    }

    /**
     * Affiche les modes disponbles.
     */
    private void afficheMenuChoixMode() {
        choixMode = "";
        boolean choixValide = false;

        do {
            System.out.println("Choix du mode de jeu");
            System.out.println("1 - Challenger - A moi de jouer");
            System.out.println("2 - Défenseur - A l'ordinateur de jouer");
            System.out.println("3 - Duel - Qui trouvera le premier ?");
            System.out.println("Faites votre choix ou 9 pour terminer.");
            choixMode = sc.nextLine();
            if ((!choixMode.equals("1")) && (!choixMode.equals("2"))  && (!choixMode.equals("3")) && (!choixMode.equals("9"))) {
                System.out.println("Choix invalide - recommencez ! \n");
            } else {
                choixValide = true;
            }
        } while ( !choixValide );
    }

    /**
     * Affiche le choix effectu&eacute;.
     *
     */
    private void afficheModeChoisi() {
        switch (choixMode) {
            case "1":
                System.out.println("Vous avez choisi le mode Challenger.");
                break;
            case "2":
                System.out.println("Vous avez choisi le mode Défenseur.");
                break;
            case "3":
                System.out.println("Vous avez choisi le mode Duel.");
                break;
            case "9":
                System.out.println("Merci et à bientôt.");
                break;
            default:
                System.out.println("ERREUR valeur incorrecte !");
                break;
        }
    }

    /**
     * Affiche les possibilit&eacute;s de continuer
     */
    private String afficheMenuContinuer() {
        String reponse = "";
        boolean choixValide = false;

        do {
            System.out.println("Rejouer au même jeu <Validez>");
            System.out.println("3 - Lancer un autre jeu");
            System.out.println("9 - Terminer.");
            reponse = sc.nextLine();
            if ((!reponse.equals("")) && (!reponse.equals("3")) && (!reponse.equals("9"))) {
                System.out.println("Choix invalide - recommencez ! \n");
            } else {
                choixValide = true;
            }
        } while ( !choixValide );

        return reponse;
    }

    /**
     * Lancement des menus et gestion des choix.
     */
    public void lanceMenu() {

        logger.debug("choixJeu : " + choixJeu + " choixMode : " + choixMode);

        String reponse = "3";
        if (choixJeu.equals("1") || choixJeu.equals("2")) { // il y a déjà eu une partie
            reponse = this.afficheMenuContinuer();
        }

        if (reponse.equals("9")) { // Fin demandée
            choixJeu = "9";
        } else if (reponse.equals("3")) { // Nouveau jeu
            this.afficheMenuChoixJeu();
            this.afficheJeuChoisi();

            if (!choixJeu.equals("9"))
            {
                this.afficheMenuChoixMode();
                this.afficheModeChoisi();
                if (choixMode.equals("9")){
                    choixJeu = "9";
                }
            }
            else
            {
                choixMode = "9"; // Pas indispensabe mais plus homogene
            }
        }
    }

}