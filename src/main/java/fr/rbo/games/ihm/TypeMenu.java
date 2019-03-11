package fr.rbo.games.ihm;


/**
 * Choix du menu principal
 */
public enum TypeMenu {
    /**
     * Choix du jeu de recherche plus ou moins
     */
    PLUS_MOINS("1"),
    /**
     * Choix du jeu Mastermind
     */
    MASTERMIND("2"),
    /**
     * Choix Terminer l'application
     */
    EXIT("9");

    String choix;

    TypeMenu(String jeu) {
        this.choix = jeu;
    }

    public String getJeu(){
        return choix;
    }

    /**
     * D&eacute;finis si le jeu fournis en entr&eacute;e est un type valide
     * @param choix valeur du choix de jeu
     * @return valeur du choix si valide ou null si invalide
     */
    public static TypeMenu getTypeMenu(String choix) {

        for(TypeMenu type : values()){
            if(choix.equals(type.getJeu())){
                return type;
            }
        }
        return null;
    }

}
