package com.gufli.pathfinding.path;

import com.gufli.pathfinding.astar.AStarGoal;
import com.gufli.pathfinding.path.Path;
import com.gufli.pathfinding.pathfinder.math.Vector;

/**
 * An abstract agent that will complete a {@link Path} as returned by {@link AStarGoal}.
 */
public interface Agent {

    Vector position();

    void moveTo(Vector vector);

}
