package org.lwjglb.game;

import org.lwjglb.engine.IGameLogic;
import org.lwjglb.engine.Window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class DummyGame implements IGameLogic {

    private int direction = 0;
    private float color = 0.0f;
    private final Renderer renderer;

    public DummyGame() {
        renderer = new Renderer();
    }

    @Override
    public void init() throws Exception {
        renderer.init();
    }

    @Override
    public void input(Window window) {
        if ( window.isKeyPressed(GLFW_KEY_UP) ) {
            direction = 1;
        } else if ( window.isKeyPressed(GLFW_KEY_DOWN) ) {
            direction = -1;
        } else {
            direction = 0;
        }
    }

    @Override
    public void update(float interval) {
        color += direction * 0.01f;
        if (color > 1) {
            color = 1.0f;
        } else if ( color < 0 ) {
            color = 0.0f;
        }
    }

    @Override
    public void render(Window window) {
        if (window.IsResized() ) {
            glViewport(0, 0, window.GetWidth(), window.GetHeight());
            window.SetResized(false);
        }
        window.SetClearColour(color, color, color, 0.0f);
        renderer.Clear();
    }

    @Override
    public void Cleanup(){
        // Deal with this later when the shader program is integrated
    }
}
