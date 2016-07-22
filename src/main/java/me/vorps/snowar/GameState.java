package me.vorps.snowar;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public enum GameState {
    WAITING("En attente"),
    INSTART("Démarrage"),
    INGAME("En jeu"),
    FINISH("Fin de la partie"),
    STOP("Eteint");

    private String label;

    GameState(String label) {
        this.label = label;
    }

    public String getLabel(){
        return label;
    }

    public static boolean isState(GameState state){
        return GameState.state == state;
    }

    public static GameState getState(){
        return GameState.state;
    }

    public static void setState(GameState state){
        GameState.state = state;
    }

    private static GameState state;


}