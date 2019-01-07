package org.lwjglb.engine;

public class GameEngine implements Runnable {

    // Members
    private static final int TARGET_FPS = 60;
    private static final int TARGET_UP = 30;
    private Window window;
    private Timer timer;
    private Thread gameLoopThread;
    private IGameLogic gameLogic;

    public GameEngine(String WindowTitle, int Width, int Height, boolean Vsync, IGameLogic GameLogic) throws Exception
    {
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        window = new Window(WindowTitle, Width, Height, Vsync);
        this.gameLogic = GameLogic;
        timer = new Timer();
    }

    public void Start() {
        String osName = System.getProperty("os.name");
        if ( osName.contains("Mac") ) {
            gameLoopThread.run();
        } else {
            gameLoopThread.start();
        }
    }

    @Override
    public void run() {
        try {
            init();
            GameLoop();
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally{
            gameLogic.Cleanup();
        }
    }

    protected void init() throws Exception {
        window.init();
        timer.init();
        gameLogic.init();
    }

    // Game Loop method
    public void GameLoop(){

        // Define times
        float msInterval = 1.0f / TARGET_UP;
        float accumulator = 0.0f;
        float elapsedTime;

        // Is running
        boolean running = true;

        while (running && !window.windowShouldClose()){
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            Input();

            while (accumulator >= msInterval){
                Update(msInterval);
                accumulator -= msInterval;
            }

            Render();

            if (!window.isVsync()){
                Sync();
            }
        }
    }

    // Sync the frame update
    private void Sync(){
        // Define
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;

        // Check position in loop
        while (timer.getTime() < endTime)
        {
            try
            {
                Thread.sleep(1);
            }
            catch(InterruptedException ie)
            {
            }
        }
    }

    protected void Input() {
        gameLogic.input(window);
    }

    protected void Update(float interval) {
        gameLogic.update(interval);
    }

    protected void Render(){
        gameLogic.render(window);
        window.Update();
    }
}
