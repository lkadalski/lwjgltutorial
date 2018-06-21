package com.lkadalski.game;

import com.lkadalski.engine.GameEngine;
import com.lkadalski.engine.IGameLogic;

public class Main {

    public static void main(String[] args) {

        try {
            boolean vSync = true;
            IGameLogic gameLogic = new DummyGame();
            GameEngine gameEngine = new GameEngine("GAME",
                    600,480, vSync, gameLogic);
            gameEngine.start();
        } catch (Exception e ){
            e.printStackTrace();
            System.exit(-1);
        }

    }
}


