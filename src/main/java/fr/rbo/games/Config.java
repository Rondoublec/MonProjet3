package fr.rbo.games;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;


/**
 * Gestion des param&egrave;tres de l'application dans le fichier de config.properties
 * @author R&eacute;my Bourdoncle
 * @version 0.1
 */
public class Config {

    private Integer nbEssaisPlusMoins, nbEssaisMastermind, nbCasesPlusMoins, nbCasesMastermind, nbValeursPlusMoins, nbValeursMastermind;
    private Boolean modeDev;
    private static final Logger logger = LogManager.getLogger();
    private static Config INSTANCE = new Config();

    /**
     * Lit le fichier properties lors de la cr&eacute;ation de l'objet
     */
    private Config() {
        lectureConfig();
    }

    /**
     * @param nbEssaisPlusMoins
     *            Nombres d'essais pour le jeu Plus ou Moins
     * @param nbEssaisMastermind
     *            Nombres d'essais pour le jeu mastermind
     * @param nbCasesPlusMoins
     *            nombre de cases du jeu Plus ou Moins
     * @param nbCasesMastermind
     *            nmbre de Cases du mastermind
     * @param nbValeursPlusMoins
     *            Nombre de valeurs possibles du jeu Plus ou Moins
     * @param nbValeursMastermind
     *            Nombre de valeurs (couleurs) utilisées par le mastermind
     * @param modeDev
     *            mode développeur (pour afficher le résultat dès le début du jeu)
     */
    public Config(Integer nbEssaisPlusMoins, Integer nbEssaisMastermind, Integer nbCasesPlusMoins, Integer nbCasesMastermind,
                  Integer nbValeursPlusMoins, Integer nbValeursMastermind, Boolean modeDev) {
        this.nbEssaisPlusMoins = nbEssaisPlusMoins;
        this.nbEssaisMastermind = nbEssaisMastermind;
        this.nbCasesPlusMoins = nbCasesPlusMoins;
        this.nbCasesMastermind = nbCasesMastermind;
        this.nbValeursPlusMoins = nbValeursPlusMoins;
        this.nbValeursMastermind = nbValeursMastermind;
        this.modeDev = modeDev;
    }

    /**
     * @param modeDev
     *            mode d&eacute;veloppeur (pour afficher le r&eacute;sultat d&egrave;s le d&eacute;but du jeu)
     */
    public void setModeDev(Boolean modeDev) {
        this.modeDev = modeDev;
    }

    /**
     * @param nbEssaisPlusMoins
     *            Nombres d'essais pour le jeu Plus ou Moins
     */
    public void setNbEssaisPlusMoins(Integer nbEssaisPlusMoins) {
        this.nbEssaisPlusMoins = nbEssaisPlusMoins;
    }

    /**
     * @param nbEssaisMastermind
     *            Nombres d'essais pour le jeu mastermind
     */
    public void setNbEssaisMastermind(Integer nbEssaisMastermind) {
        this.nbEssaisMastermind = nbEssaisMastermind;
    }

    /**
     * @param nbCasesPlusMoins
     *            nombre de cases du jeu Plus ou Moins
     */
    public void setNbCasesPlusMoins(Integer nbCasesPlusMoins) {
        this.nbCasesPlusMoins = nbCasesPlusMoins;
    }

    /**
     * @param nbCasesMastermind
     *            Nombre de cases de la ligne du mastermind
     */
    public void setNbCasesMastermind(Integer nbCasesMastermind) {
        this.nbCasesMastermind = nbCasesMastermind;
    }

    /**
     * @param nbValeursPlusMoins
     *            Nombre de valeurs (couleurs) pour le mastermind
     */
    public void setNbValeursPlusMoins(Integer nbValeursPlusMoins) {
        this.nbValeursPlusMoins = nbValeursPlusMoins;
    }

    /**
     * @param nbValeursMastermind
     *            Nombre de valeurs (couleurs) pour le mastermind
     */
    public void setNbValeursMastermind(Integer nbValeursMastermind) {
        this.nbValeursMastermind = nbValeursMastermind;
    }

    /**
     * @return mode d&eacute;veloppeur (pour afficher le r&eacute;sultat d&egrave;s le d&eacute;but du jeu)
     */
    public boolean getModeDev() {
        return this.modeDev;
    }

    /**
     * @return Nombres d'essais pour le jeu Plus ou Moins
     */
    public int getNbEssaisPlusMoins() {
        return this.nbEssaisPlusMoins;
    }

    /**
     * @return Nombres d'essais pour le jeu mastermind
     */
    public int getNbEssaisMastermind() {
        return this.nbEssaisMastermind;
    }

    /**
     * @return Nombre de cases du jeu Plus ou Moins
     */
    public int getNbCasesPlusMoins() {
        return this.nbCasesPlusMoins;
    }

    /**
     * @return Nombre de cases de la ligne du mastermind
     */
    public int getNbCasesMastermind() {
        return this.nbCasesMastermind;
    }

    /**
     * @return Nombre de valeurs du jeu Plus ou Moins
     */
    public int getNbValeursPlusMoins() {
        return this.nbValeursPlusMoins;
    }

    /**
     * @return Nombre de valeurs (couleurs) pour le mastermind
     */
    public int getNbValeursMastermind() {
        return this.nbValeursMastermind;
    }

    /**
     * Sauvegarde les param&egrave;tres dans le fichier config.properties
     */
    public void sauvegardeConfig() {
        Properties prop = new Properties();
        OutputStream output = null;
        if (nbEssaisPlusMoins < 2|| nbEssaisPlusMoins > 20) {
            nbEssaisPlusMoins = 10;
        }
        if (nbEssaisMastermind < 2|| nbEssaisMastermind > 20) {
            nbEssaisMastermind = 10;
        }
        if (nbCasesPlusMoins < 2|| nbCasesPlusMoins > 9) {
            nbCasesPlusMoins = 4;
        }
        if (nbCasesMastermind < 2|| nbCasesMastermind > 5) {
            nbCasesMastermind = 4;
        }
        if (nbValeursPlusMoins < 5||nbValeursPlusMoins > 10) {
            nbValeursPlusMoins = 10;
        }
        if (nbValeursMastermind < 4|| nbValeursMastermind > 10) {
            nbValeursMastermind = 6;
        }
        File f = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        String cheminProperties =f.getParent() + "\\config.properties";
        logger.debug("Output - Localisation du fichier de config : " + cheminProperties);
        try {
            output = new FileOutputStream(cheminProperties);
            prop.setProperty("nbEssaisPlusMoins", nbEssaisPlusMoins.toString());
            prop.setProperty("nbEssaisMastermind", nbEssaisMastermind.toString());
            prop.setProperty("nbCasesPlusMoins", nbCasesPlusMoins.toString());
            prop.setProperty("nbCasesMastermind", nbCasesMastermind.toString());
            prop.setProperty("nbValeursPlusMoins", nbValeursPlusMoins.toString());
            prop.setProperty("nbValeursMastermind", nbValeursMastermind.toString());
            prop.setProperty("modeDev", modeDev.toString());
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * lit les param&egrave;tres dans le fichier config.properties
     */
    public void lectureConfig() {
        Properties prop = new Properties();
        InputStream input = null;
        File f = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        String cheminProperties =f.getParent() + "\\config.properties";
        logger.debug("Input - Localisation du fichier de config : " + cheminProperties);
        try {
            input = new FileInputStream(cheminProperties);
            prop.load(input);
            nbEssaisPlusMoins = Integer.parseInt(prop.getProperty("nbEssaisPlusMoins"));
            nbEssaisMastermind = Integer.parseInt(prop.getProperty("nbEssaisMastermind"));
            nbCasesPlusMoins = Integer.parseInt(prop.getProperty("nbCasesPlusMoins"));
            nbCasesMastermind = Integer.parseInt(prop.getProperty("nbCasesMastermind"));
            nbValeursMastermind = Integer.parseInt(prop.getProperty("nbValeursMastermind"));
            nbValeursPlusMoins = Integer.parseInt(prop.getProperty("nbValeursPlusMoins"));
            modeDev = Boolean.parseBoolean(prop.getProperty("modeDev"));
            logger.debug("lecture du fichier config.properties...");
        } catch (IOException ex) {
            // ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                nbEssaisPlusMoins = 10;
                nbEssaisMastermind = 10;
                nbCasesPlusMoins = 4;
                nbCasesMastermind = 4;
                nbValeursPlusMoins = 10;
                nbValeursMastermind = 6;
                modeDev = false;
                sauvegardeConfig();
                logger.debug("lecture du fichier config.properties impossible. Utilisation de valeurs par défaut.");
            }
        }
        //Gestion des bornes des valeurs possibles
        if (nbEssaisPlusMoins < 2|| nbEssaisPlusMoins > 20) {
            nbEssaisPlusMoins = 10;
            sauvegardeConfig();
        }
        if (nbEssaisMastermind < 2|| nbEssaisMastermind > 20) {
            nbEssaisMastermind = 10;
            sauvegardeConfig();
        }
        if (nbCasesPlusMoins < 2|| nbCasesPlusMoins > 9) {
            nbCasesPlusMoins = 4;
            sauvegardeConfig();
        }
        if (nbCasesMastermind < 2|| nbCasesMastermind > 5) {
            nbCasesMastermind = 4;
            sauvegardeConfig();
        }
        if (nbValeursPlusMoins < 5|| nbValeursPlusMoins > 10) {
            nbValeursPlusMoins = 10;
            sauvegardeConfig();
        }
        if (nbValeursMastermind < 4|| nbValeursMastermind > 10) {
            nbValeursMastermind = 6;
            sauvegardeConfig();
        }

    }

    /**
     * @return l'instance de l'objet config
     */
    public static Config getInstance() {
        return INSTANCE;
    }

}
