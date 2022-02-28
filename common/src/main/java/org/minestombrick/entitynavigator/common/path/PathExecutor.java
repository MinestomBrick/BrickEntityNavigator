package org.minestombrick.entitynavigator.common.path;

import org.minestombrick.entitynavigator.common.pathfinder.math.Vector;

import java.util.ListIterator;

public interface PathExecutor {

    void run(Agent agent, Vector node, ListIterator<Vector> remainingPath);

}
