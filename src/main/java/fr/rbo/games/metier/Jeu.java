package fr.rbo.games.metier;

public class Jeu {

    protected static final boolean PROPOSITION = true;
    protected static final boolean SECRET = false;
    protected static final boolean HUMAIN = true;
    protected static final boolean ORDINATEUR = false;

    private String nomDuJeu;
    private String descriptionDuJeu;

    public Jeu(String nomDuJeu, String descriptionDuJeu){
        this.nomDuJeu = nomDuJeu;
        this.descriptionDuJeu = descriptionDuJeu;
    }

    public String getNomDuJeu() {
        return nomDuJeu;
    }

    public String getDescriptionDuJeu() {
        return descriptionDuJeu;
    }
}
