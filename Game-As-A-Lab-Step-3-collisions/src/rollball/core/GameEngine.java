package rollball.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import rollball.model.*;
import rollball.common.P2d;
import rollball.common.V2d;
import rollball.graphics.*;
import rollball.input.*;

/**
 * The main game engine that controls the game loop and handles commands.
 */
public class GameEngine implements Controller {

	private static final int CMD_QUEUE_SIZE = 100; // Size of the command queue

	private long period = 20; // Time period for the game loop in milliseconds

	private World world; // The game world
	private Scene view; // The view that renders the game world
	private BlockingQueue<Command> cmdQueue; // Queue for commands that control the game

	public GameEngine() {
		// Initialize the command queue
		cmdQueue = new ArrayBlockingQueue<>(CMD_QUEUE_SIZE);
	}

	/**
	 * Sets up the game world and the view.
	 */
	public void setup() {
		// Setup the game world and the view
		world = new World(new RectBoundingBox(new P2d(-9, 8), new P2d(9, -8)));
		world.setBall(new Ball(new P2d(0, 0), 1, new V2d(8, 3)));
		world.addPickUp(new PickUpObj(new P2d(0, 5), 1));
		world.addPickUp(new PickUpObj(new P2d(6, 0), 1));
		world.addPickUp(new PickUpObj(new P2d(-4, 3), 1));
		world.addPickUp(new PickUpObj(new P2d(-1, -7), 1));

		view = new Scene(world, 600, 600, 20, 20);
		view.setInputController(this);
	}

	/**
	 * The main game loop that updates the game state and renders the view.
	 * Executes the next command if available and sleeps until the next frame if necessary.
	 */
	public void mainLoop() {
		// Main game loop
		long lastTime = System.currentTimeMillis();
		while (true) {
			long currentTime = System.currentTimeMillis();
			int elapsedTime = (int) (currentTime - lastTime);

			// Execute the next command if available
			Command cmd = cmdQueue.poll();
			if (cmd != null) {
				cmd.execute(world);
			}

			// HERE ORDER IS FUNDAMENTAL
			// Update the game state and render the view
			world.updateState(elapsedTime);
			view.render();

			// Sleep until the next frame if necessary
			long timeTaken = System.currentTimeMillis() - currentTime;
			if (timeTaken < period) {
				try {
					Thread.sleep(period - timeTaken);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}

			lastTime = currentTime;
		}
	}

	@Override
	public void notifyCommand(Command cmd) {
		// Add a command to the command queue
		cmdQueue.add(cmd);
	}
}