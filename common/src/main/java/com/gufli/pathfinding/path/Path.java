package com.gufli.pathfinding.path;

import com.gufli.pathfinding.astar.AStarGoal;
import com.gufli.pathfinding.pathfinder.math.Vector;

/**
 * An abstract plan returned by the {@link AStarGoal} that should be run until completion.
 */
public interface Path {

    boolean isFinished();

    Vector currentVector();

    Vector destination();

    void next();

    int length();



}
