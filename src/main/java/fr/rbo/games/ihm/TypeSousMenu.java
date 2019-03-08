package fr.rbo.games.ihm;

public enum TypeSousMenu {

    CHALLENGER("1"),
    DEFENSEUR("2"),
    DUEL("3"),
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
