package fr.rbo.games.metier;

import fr.rbo.games.metier.JeuPlusMoins;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class JeuPlusMoinsTest {

//    JeuPlusMoins plusMoins = new JeuPlusMoins();
    JeuPlusMoins plusMoins = new JeuPlusMoins("Recherche plus ou moins","",
            5,4,10);

    @Test
    public void Given_JeuPlusMoins_WhenCompareSaisieEgale_Then_ReturnLigneEgal() {

        int nbOccurences = 4;
        String resultat = plusMoins.compareSaisie(new int[]{1,2,3,4},new int[]{1,2,3,4},nbOccurences);
        assertEquals("====", resultat);
    }

    @Test
    public void Given_JeuPlusMoins_WhenCompareSaisieMoins_Then_ReturnLignePlus() {

        int nbOccurences = 4;
        String resultat = plusMoins.compareSaisie(new int[]{2,3,4,5},new int[]{1,2,3,4},nbOccurences);
        assertEquals("++++", resultat);
    }

    @Test
    public void Given_JeuPlusMoins_WhenCompareSaisiePlus_Then_ReturnLigneMoins() {

        int nbOccurences = 4;
        String resultat = plusMoins.compareSaisie(new int[]{1,2,3,4},new int[]{2,3,4,5},nbOccurences);
        assertEquals("----", resultat);
    }

    @Test
    public void Given_JeuPlusMoins_WhenCompareSaisieEgaleLongueurSix_Then_ReturnLigneEgalLongueurSix() {

        int nbOccurences = 6;
        String resultat = plusMoins.compareSaisie(new int[]{0,1,2,3,4,5},new int[]{0,1,2,3,4,5},nbOccurences);
        assertEquals("======", resultat);
    }

}