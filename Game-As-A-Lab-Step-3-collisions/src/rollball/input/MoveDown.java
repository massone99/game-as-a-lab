package rollball.input;

import rollball.common.V2d;
import rollball.model.*;

/**
 * MoveDown is a command that moves the ball down by setting its velocity to a downward direction.
 * The velocity magnitude is preserved.
 */
public class MoveDown implements Command {
	@Override
	public void execute(World scene) {
		Ball ball = scene.getBall();
		double speed = ball.getCurrentVel().module();
		ball.setVel(new V2d(0,-1).mul(speed));
	}

}
