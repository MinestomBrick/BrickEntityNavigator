package org.minestombrick.entitynavigator.common.path;

import org.minestombrick.entitynavigator.common.astar.AStarGoal;
import org.minestombrick.entitynavigator.common.pathfinder.math.Vector;

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
