package org.lwjglb.game;

import org.lwjglb.engine.GameEngine;
import org.lwjglb.engine.IGameLogic;

public class Main {

    public static void main(String[] args) {
        try{
            IGameLogic gameLogic = new DummyGame();
            boolean vSync = true;
            GameEngine gameEng = new GameEngine("GAME", 600, 480, vSync, gameLogic);
            gameEng.Start();
        }
        catch(Exception exc){
            exc.printStackTrace();
            System.exit(-1);
        }
    }
}
