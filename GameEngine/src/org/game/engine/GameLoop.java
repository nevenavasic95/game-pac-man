

package org.game.engine;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameLoop extends Thread {

	private final Game game;
	private final GameCanvas canvas;
    private boolean stopped;

	public GameLoop(Game game, GameCanvas canvas) {
		this.game = game;
		this.canvas = canvas;
        this.stopped = false;
	}

    public void stopGame() {
        stopped = true;
    }

	@Override
	public void run() {
		game.init();

		while (!game.isOver() && !stopped) {

			try {
				Thread.sleep(game.getDelay());
			} catch (InterruptedException ex) {
				Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
