

package org.game.engine;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameLoop extends Thread {

	private final Game game;
	private final GameCanvas canvas;

	public GameLoop(Game game, GameCanvas canvas) {
		this.game = game;
		this.canvas = canvas;
	}
	

	@Override
	public void run() {
		game.init();

		while (!game.isOver()) {
			
			game.update();
			canvas.repaint();

			try {
				Thread.sleep(game.getDelay());
			} catch (InterruptedException ex) {
				Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
