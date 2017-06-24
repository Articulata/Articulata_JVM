package com.articulatagame;

import javax.annotation.Nullable;

public class LogicThread extends Thread {
    private static final double TPS_TARGET = 30.0; //double for math accuracy
    private static final double TIME_BETWEEN_UPDATES = 1000000000 / TPS_TARGET;
    private static final int MAX_DELAYED_UPDATES = 50;
    private double lastUpdateTime = System.nanoTime();
    private boolean shouldBeRunning = true;
    private boolean paused = false;

    @Nullable //temp nullable for easy testing
    private GameState state;


    public LogicThread(String side, GameState initalState) {
        super(side + " Logic Thread");
        setState(initalState);
    }

    @Override
    public void run() {
        while (shouldBeRunning) {
            double now = System.nanoTime();
            int updateCount = 0;
            if (!paused && state != null) {
                while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_DELAYED_UPDATES) {
                    state.update();
                    updateCount++;
                    lastUpdateTime += TIME_BETWEEN_UPDATES;
                }

                if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
                    lastUpdateTime = now - TIME_BETWEEN_UPDATES;
                }
                while (now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                    Thread.yield();
                    try {
                        sleep(1);
                    } catch (InterruptedException e) {
                        //ignore
                    }
                    now = System.nanoTime();

                }
            }
        }
    }

    public void setState(@Nullable GameState state) {
        if (this.state != null) {
            this.state.onUnload();
        }

        this.state = state;

        if (this.state != null) {
            this.state.onLoad();
        }
    }

    public void shutdown() {
        shouldBeRunning = false;
        setState(null);
    }
}
