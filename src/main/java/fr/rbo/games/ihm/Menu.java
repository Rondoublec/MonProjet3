package fr.rbo.games.ihm;

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
    private TypeMenu choixJeu;
    private TypeSousMenu choixMode;
    private static final Logger logger = LogManager.getLogger();

    /**
     * Choix de jeu
     * @return choixJeu (9 si fin demand&eacute;e).
     */
    public TypeMenu getChoixJeu() {
        return choixJeu;
    }

    /**
     * Mode de jeu
     * @return choixMode (9 si fin demand&eacute;e).
     */
    public TypeSousMenu getChoixMode() {
        return choixMode;
    }

    /**
     * Affiche les jeux disponbles.
     */
    private void afficheMenuChoixJeu() {
        boolean choixValide = false;

        do {
            System.out.println("=========================================");
            System.out.println("            == G A M E S ==              ");
            System.out.println("     A quel jeu voulez-vous jouer ?      ");
            System.out.println("=========================================");
            System.out.println("1 - Plus ou Moins");
            System.out.println("2 - Mastermind");
            System.out.println("Faites votre choix ou 9 pour terminer -> ");
            choixJeu = TypeMenu.getTypeMenu(sc.nextLine());
            if (choixJeu == null) {
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
            case PLUS_MOINS:
                System.out.println("=========================================");
                System.out.println("         P L U S  ou  M O I N S          ");
                break;
            case MASTERMIND:
                System.out.println("=========================================");
                System.out.println("          M A S T E R M I N D            ");
                break;
            case EXIT:
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
        boolean choixValide = false;

        do {
            System.out.println("    Quel défis voulez vous relever ?     ");
            System.out.println("=========================================");
            System.out.println("1 - Challenger - Trouvez la combinaison et emportez le trophé.");
            System.out.println("2 - Défenseur  - L'ordinateur trouvera-t-il votre code secret ?");
            System.out.println("3 - Duel       - Affrontez la machine !");
            System.out.println("Faites votre choix ou 9 pour terminer -> ");
            choixMode = TypeSousMenu.getTypeSousMenu(sc.nextLine());
            if (choixMode == null) {
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
            case CHALLENGER:
                System.out.println("=========================================");
                System.out.println(" CHALLENGER : Trouvez la combinaison     ");
                System.out.println("=========================================");
                break;
            case DEFENSEUR:
                System.out.println("=========================================");
                System.out.println(" DEFENSEUR : Saisissez votre code secret ");
                System.out.println("=========================================");
                break;
            case DUEL:
                System.out.println("=========================================");
                System.out.println(" DUEL : Affrontez la machine             ");
                System.out.println("=========================================");
                break;
            case EXIT:
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
        if (choixJeu == TypeMenu.PLUS_MOINS || choixJeu == TypeMenu.MASTERMIND) { // il y a déjà eu une partie
            reponse = this.afficheMenuContinuer();
        }

        if (reponse.equals("9")) { // Fin demandée
            choixJeu = TypeMenu.EXIT;
        } else if (reponse.equals("3")) { // Nouveau jeu
            this.afficheMenuChoixJeu();
            this.afficheJeuChoisi();

            if (!choixJeu.equals(TypeMenu.EXIT))
            {
                this.afficheMenuChoixMode();
                this.afficheModeChoisi();
                if (choixMode.equals(TypeSousMenu.EXIT)){
                    choixJeu = TypeMenu.EXIT;
                }
            }
            else
            {
                choixMode = TypeSousMenu.EXIT; // Pas indispensabe mais plus homogene
            }
        }
    }

}