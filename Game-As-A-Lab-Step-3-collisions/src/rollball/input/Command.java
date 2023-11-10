package rollball.input;

import rollball.model.*;

/**
 * This interface represents a command that can be executed on a World scene.
 */
public interface Command {
	/**
	 * Executes the command on the given World scene.
	 * @param scene the World scene on which to execute the command
	 */
	void execute(World scene);
}
