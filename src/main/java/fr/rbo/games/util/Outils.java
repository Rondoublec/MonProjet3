package fr.rbo.games.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Scanner;

/**
 * M&eacute;thodes communes r&eacute;utilisables pour les jeux
 * @author R&eacute;my Bourdoncle
 * @version 0.1
 */
public class Outils {

    private static final Logger logger = LogManager.getLogger();

    /**
     * Tirage al&eacute;atoire de la s&eacute;rie &agrave; trouver.
     *
     * @param nbElements = nombre d'&eacute;l&eacute;ments du tableau
     * @param plage de valeurs 10 = (0 à 9), 6 = (0 à 5), ...
     * @param debug = si true activation des traces
     * @return tirage = Tableau de "nbElements" &eacute;l&eacute;ments num&eacute;riques, valeurs possibles 0 &agrave; 9.
     */
    public int[] tirageDuNombre(int nbElements, int plage, boolean debug){
        int[] tirage = new int[nbElements];
        for (int i=0; i < nbElements; i++) {
            tirage[i] = (int) (Math.random() * (plage-1));
        }
        if (debug) {
            System.out.print(" Mode DEV - tirage = " + Arrays.toString(tirage).replace(", ", ""));
        }

        logger.info("tirageDuNombre " + Arrays.toString(tirage).replace(", ", ""));

        return tirage;
    }
    /**
     * Saisie de l'utilisateur.
     *
     * @param proposition = type de donn&eacute;es, si proposition (true) l'utilisateur entre son essai. Si non, il s'agit de la combinaison &agrave; faire d&eacute;couvrir.
     * @param nbElements = nombre d'&eacute;l&eacute;ments du tableau
     * @param nbMax = nombre de valeurs possibles (ou couleurs)
     * @param debug = si true activation des traces
     * @return chiffreSaisi = Tableau de "nbElements" &eacute;l&eacute;ments num&eacute;riques, valeurs possibles 0 &agrave; 9.
     */
    public int[] saisieNombre(int nbElements, int nbMax, boolean proposition, boolean debug){
        int[] chiffreSaisi = new int[nbElements];
        Scanner sc = new Scanner(System.in);
        boolean nombreValide = false;
        nbMax = nbMax - 1;
        do {
            if (proposition){
                System.out.println("\nEntrez votre proposition de " + nbElements + " chiffres (de 0 à " + nbMax + ") ?");
            } else {
                System.out.println("\nEntrez la combinaison de " + nbElements + " chiffres (de 0 à " + nbMax + ") à trouver par l'ordnateur ?");
            }
            String saisie = sc.nextLine();
            if (saisie.length() == nbElements) {
                try {
                    int verif = Integer.parseInt(saisie);
                    nombreValide = true; // pas d'exception, donc c'est numerique
                    for (int i=0; i < nbElements; i++) {
                        chiffreSaisi[i] = (int) saisie.charAt(i) - 48;
                        if (chiffreSaisi[i] > nbMax) {
                            nombreValide = false;
                            System.out.println("Cette valeur [" + chiffreSaisi[i] + "] est trop grande !");
                        }
                    }
                } catch(NumberFormatException e) {
                    System.out.println("Cette valeur [" + saisie + "] n'est pas numérique, saisissez un nombre !");
                }
            }
            else {
                System.out.println("Cette valeur [" + saisie + "] ne fait pas " + nbElements + " caractères.");
            }
        } while (!nombreValide);

        return chiffreSaisi;
    }

    /**
     * Fabrication d'une nouvelleChaine de nbOccurences du mod&egrave;le.
     *
     * @param modele = Mod&egrave;le de chaine de caract&egrave;re &agrave; reproduire.
     * @param nbOccurence = nombre d'occurences &agrave; fabriquer
     * @return nouvelleChaine = Chaine r&eacute;sultante du traitement.
     */
    public String fabriqueChaine(String modele, int nbOccurence){
        String nouvelleChaine = "";
        for (int i=0; i < nbOccurence; i++){
            nouvelleChaine = nouvelleChaine + modele;
        }
        return nouvelleChaine;
    }

}

