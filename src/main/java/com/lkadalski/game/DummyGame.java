package com.lkadalski.game;

import com.lkadalski.engine.GameItem;
import com.lkadalski.engine.IGameLogic;
import com.lkadalski.engine.Window;
import com.lkadalski.engine.graph.Mesh;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class DummyGame implements IGameLogic {


    private int disXInc = 0;
    private int disYInc = 0;
    private int disZInc = 0;
    private int scaleInc = 0;
    private int direction = 0;
    private float color = 0.0f;
    private final Renderer renderer;
    private GameItem[] gameItems;

    public DummyGame() {
        this.renderer = new Renderer();
    }


    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);
        float[] positions = new float[]{
                -0.5f, 0.5f, -5.55f,
                -0.5f, -0.5f, -5.55f,
                0.5f, -0.5f, -5.55f,
                0.5f, 0.5f, -5.65f,
        };
        float[] colours = new float[]{
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f,
        };
        int[] indices = new int[]{
                0, 1, 3, 3, 1, 2,
        };

        Mesh mesh = new Mesh(positions, colours, indices);
        GameItem gameItem = new GameItem(mesh);
        gameItem.setPosition(0, 0, -2);
        gameItems = new GameItem[]{gameItem};
    }

    @Override
    public void input(Window window) {
        disXInc = 0;
        disYInc = 0;
        disZInc = 0;
        scaleInc = 0;
        direction = 1;
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            disYInc = 1;
        } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            disYInc = -1;
        } else if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            disXInc = -1;
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            disXInc = 1;
        } else if (window.isKeyPressed(GLFW_KEY_A)) {
            disZInc = -1;
        } else if (window.isKeyPressed(GLFW_KEY_Q)) {
            disZInc = 1;
        } else if (window.isKeyPressed(GLFW_KEY_Z)) {
            scaleInc = -1;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            scaleInc = 1;
        } else if (window.isKeyPressed(GLFW_KEY_SPACE)) {
            direction = 90;
        }
    }

    @Override
    public void update(float interval) {
        for (GameItem gameItem : gameItems) {
            Vector3f itemsPos = gameItem.getPosition();
            float posx = itemsPos.x + disXInc * 0.05f;
            float posy = itemsPos.y + disYInc * 0.05f;
            float posz = itemsPos.z + disZInc * 0.05f;
            gameItem.setPosition(posx, posy, posz);


            //update scale
            float scale = gameItem.getScale();
            scale += scaleInc * 0.55f;
            if (scale < 0) {
                scale = 0;
            }
            gameItem.setScale(scale);
            float rotation = gameItem.getRotation().z + direction * 1.5f;
            if (rotation > 360) {
                rotation = 0;
            }
            gameItem.setRotation(0, 0, rotation);
        }

        //manipulacja kolorem
//        color += direction * 0.01f;
//        if (color > 1) {
//            color = 1.0f;
//        } else if (color < 0) {
//            color = 0.0f;
//        }
    }

    @Override
    public void render(Window window) {
        renderer.render(window, gameItems);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        for (GameItem gameItem : gameItems) {
            gameItem.getMesh().cleanUp();
        }
    }
}
