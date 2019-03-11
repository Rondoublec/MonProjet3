package fr.rbo.games.ihm;

/**
 * Choix du sous menu
 */
public enum TypeSousMenu {

    /**
     * Choix du mode dans lequel l'humain doit trouver la solution
     */
    CHALLENGER("1"),
    /**
     * Choix du mode dans lequel l'ordianteur doit trouver la solution
     */
    DEFENSEUR("2"),
    /**
     * Choix du mode dans lequel l'humain et l'ordinateur s'affrontent, le premier qui trouve a gagn&eacute;
     */
    DUEL("3"),
    /**
     * Choix Terminer l'application
     */
    EXIT("9");

    String choix;

    TypeSousMenu(String mode) {
        this.choix = mode;
    }

    public String getMode(){
        return choix;
    }

    /**
     * D&eacute;finis si le mode de jeu fournis en entr&eacute;e est valide
     * @param choix valeur du choix du mode de jeu
     * @return valeur du choix si valide ou null si invalide
     */
    public static TypeSousMenu getTypeSousMenu(String choix) {

        for(TypeSousMenu type : values()){
            if(choix.equals(type.getMode())){
                return type;
            }
        }
        return null;
    }

}
