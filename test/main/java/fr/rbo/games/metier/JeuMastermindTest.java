package fr.rbo.games.metier;

import fr.rbo.games.metier.JeuMastermind;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class JeuMastermindTest {
/*
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    private void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    private void restoreStreams() {
        System.setOut(System.out);
    }
*/

    JeuMastermind mastermind = new JeuMastermind();

    @Test
    public void Given_JeuMastermind_WhenCompareSaisieDifferente_Then_ReturnZero() {

        int nbOccurences = 4;
        int resultat = mastermind.compareSaisie(new int[]{1,2,3,4},new int[]{5,6,7,8}, nbOccurences);
        assertEquals(0, resultat);
    }
    @Test
    public void Given_JeuMastermind_WhenCompareSaisieEgale_Then_ReturnToutEgal() {

        int nbOccurences = 4;
        int resultat = mastermind.compareSaisie(new int[]{0,0,0,0},new int[]{0,0,0,0}, nbOccurences);
        assertEquals(nbOccurences, resultat);
    }

    @Test
    public void Given_JeuMastermind_WhenCompareSaisieEgaleLonguerCinq_Then_ReturnToutEgalLonguerCinq() {

        int nbOccurences = 5;
        int resultat = mastermind.compareSaisie(new int[]{1,0,0,0,0},new int[]{1,0,0,0,0}, nbOccurences);
        assertEquals(nbOccurences, resultat);
    }

}