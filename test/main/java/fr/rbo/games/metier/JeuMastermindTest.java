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

//    JeuMastermind mastermind = new JeuMastermind();
    JeuMastermind mastermind = new JeuMastermind("Mastermind","",
            5,4,6);

    @Test
    public void Given_JeuMastermind_WhenCompareSaisieDifferente_Then_ReturnZero() {

        int nbCases = 4;
        int resultat = mastermind.score(new int[]{1,2,3,4},new int[]{5,6,7,8}, nbCases,9);
        assertEquals(0, resultat);
    }
    @Test
    public void Given_JeuMastermind_WhenCompareSaisieEgale_Then_ReturnToutEgal() {

        int nbCases = 4;
        int resultat = mastermind.score(new int[]{0,0,0,0},new int[]{0,0,0,0}, nbCases,6);
        assertEquals(nbCases*10, resultat);
    }

    @Test
    public void Given_JeuMastermind_WhenCompareSaisieEgaleLonguerCinq_Then_ReturnToutEgalLonguerCinq() {

        int nbCases = 5;
        int resultat = mastermind.score(new int[]{1,0,0,0,0},new int[]{1,0,0,0,0}, nbCases,6);
        assertEquals(nbCases*10, resultat);
    }

}