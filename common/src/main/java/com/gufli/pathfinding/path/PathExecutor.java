package com.gufli.pathfinding.path;

import com.gufli.pathfinding.pathfinder.math.Vector;

import java.util.ListIterator;

public interface PathExecutor {

    void run(Agent agent, Vector node, ListIterator<Vector> remainingPath);

}
