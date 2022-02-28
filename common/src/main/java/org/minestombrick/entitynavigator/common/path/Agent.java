package org.minestombrick.entitynavigator.common.path;

import org.minestombrick.entitynavigator.common.astar.AStarGoal;
import org.minestombrick.entitynavigator.common.pathfinder.math.Vector;

/**
 * An abstract agent that will complete a {@link Path} as returned by {@link AStarGoal}.
 */
public interface Agent {

    Vector position();

    void moveTo(Vector vector);

}
