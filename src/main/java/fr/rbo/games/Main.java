package fr.rbo.games;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Programme de jeux "Recherche plus ou moins" ou "mastermind".
 * 3 modes sont possibles<br>
 * - Challenger = joueur humain<br>
 * - D&eacute;fenseur = joueur ordinateur<br>
 * - Duel = humain contre ordinateur.
 * @author R&eacute;my Bourdoncle
 * @version 0.1
 */
public class Main {

    public static boolean DEBUG;
    public static int NB_DIGIT_PLUS_MOINS;
    public static int ESSAIS_MAX_PLUS_MOINS;
    public static int NB_VALEURS_PLUS_MOINS;
    public static int NB_DIGIT_MASTERMIND;
    public static int ESSAIS_MAX_MASTERMIND;
    public static int NB_VALEURS_MASTERMIND;

    public static String GAGNE;

    private static Config config = Config.getInstance();
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("dev")) {
                config.setModeDev(true);
                config.sauvegardeConfig();
            }
            if (args[i].equals("nodev")) {
                config.setModeDev(false);
                config.sauvegardeConfig();
            }
        }

        DEBUG = config.getModeDev();
        NB_DIGIT_PLUS_MOINS = config.getNbCasesPlusMoins();
        ESSAIS_MAX_PLUS_MOINS = config.getNbEssaisPlusMoins();
        NB_VALEURS_PLUS_MOINS = config.getNbValeursPlusMoins();
        NB_DIGIT_MASTERMIND = config.getNbCasesMastermind();
        ESSAIS_MAX_MASTERMIND = config.getNbEssaisMastermind();
        NB_VALEURS_MASTERMIND = config.getNbValeursMastermind();

        logger.debug("Config : DEBUG " + DEBUG + " NB_DIGIT_PLUS_MOINS " + NB_DIGIT_PLUS_MOINS + " ESSAIS_MAX_PLUS_MOINS " + ESSAIS_MAX_PLUS_MOINS + " NB_VALEURS_PLUS_MOINS " + NB_VALEURS_PLUS_MOINS);
        logger.debug("NB_DIGIT_MASTERMIND " + NB_DIGIT_MASTERMIND + " ESSAIS_MAX_MASTERMIND " + ESSAIS_MAX_MASTERMIND + " NB_VALEURS_MASTERMIND " + NB_VALEURS_MASTERMIND);

/*
        logger.debug("msg de debogage");
        logger.info("msg d'information");
        logger.warn("msg d'avertissement");
        logger.error("msg d'erreur");
        logger.fatal("msg d'erreur fatale");
*/

        Menu menu = new Menu();

        do {

            logger.info("menu.lancemenu");

            menu.lanceMenu();

            logger.debug("Choix jeu : " + menu.getChoixJeu() + " Choix mode : " + menu.getChoixMode());

            switch (menu.getChoixJeu()) {
                case "1":
                    JeuPlusMoins plusmoins = new JeuPlusMoins();
                    String resultatPlusMoins = plusmoins.lancePlusMoins(menu.getChoixMode());
                    System.out.println("\nresultatPlusMoins = " + resultatPlusMoins);
                    break;
                case "2":
                    JeuMastermind mastermind = new JeuMastermind();
                    String resultatMastermind = mastermind.lanceMastermind(menu.getChoixMode());
                    System.out.println("\nresultatMastermind = " + resultatMastermind);
                    break;
                case "9":
                    System.out.println("Fin demandée par l'utilisateur.");
                    break;
                default:
                    System.err.println("Choix inconnu ... Fin anormale");
                    System.exit(9);
                    break;
            }
        } while (!menu.getChoixJeu().equals("9"));

        System.exit(0);
    }
}
