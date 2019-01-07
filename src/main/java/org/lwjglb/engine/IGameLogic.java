package org.lwjglb.engine;

public interface IGameLogic {

    // Elements
    void init() throws Exception;
    void input(Window window);
    void update(float interval);
    void render(Window window);
    void Cleanup();
}
