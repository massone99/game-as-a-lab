package rollball.model;

import java.util.ArrayList;
import java.util.List;
import rollball.common.*;

/**
 * The World class represents the game world, which contains the ball and pick up objects.
 * It also manages the boundaries of the game world and detects collisions between the ball and pick up objects.
 */
public class World {

	private List<PickUpObj> picks = new ArrayList<>(); // List of pick up objects
	private Ball ball; // The ball object
	private RectBoundingBox mainBBox; // The main bounding box

	public World(RectBoundingBox bbox) {
		mainBBox = bbox; // Initialize the main bounding box
	}

	public void setBall(Ball ball) {
		this.ball = ball; // Set the ball object
	}

	public void addPickUp(PickUpObj obj) {
		picks.add(obj); // Add a pick up object
	}

	public void updateState(int dt) {
		picks.forEach(obj -> obj.updateState(dt)); // Update state of each pick up object
		ball.updateState(dt); // Update state of the ball

		checkBoundaries(); // Check if the ball is within the boundaries
		checkCollisions(); // Check for collisions between the ball and pick up objects
	}

	private void checkBoundaries() {
		// Check if the ball is within the boundaries and adjust its position and velocity if necessary
		P2d pos = ball.getCurrentPos();
		P2d ul = mainBBox.getULCorner();
		P2d br = mainBBox.getBRCorner();
		double r = ball.getRadius();
		if (pos.y + r > ul.y || pos.y - r < br.y) {
			ball.setPos(new P2d(pos.x, pos.y + r > ul.y ? ul.y - r : br.y + r));
			ball.flipVelOnY();
		}
		if (pos.x + r > br.x || pos.x - r < ul.x) {
			ball.setPos(new P2d(pos.x + r > br.x ? br.x - r : ul.x + r, pos.y));
			ball.flipVelOnX();
		}
	}

	private void checkCollisions() {
		// Check for collisions between the ball and pick up objects and remove the collided pick up object
		P2d ballpos = ball.getCurrentPos();
		double radius = ball.getRadius();
		picks.removeIf(obj -> obj.getBBox().isCollidingWith(ballpos, radius));
	}

	public List<GameObject> getSceneEntities() {
		// Get a list of all game objects
		List<GameObject> entities = new ArrayList<>(picks);
		entities.add(ball);
		return entities;
	}

	public RectBoundingBox getBBox() {
		return mainBBox; // Get the main bounding box
	}

	public Ball getBall() {
		return ball; // Get the ball object
	}
}