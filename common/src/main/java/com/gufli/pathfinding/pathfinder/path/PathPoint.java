package com.gufli.pathfinding.pathfinder.path;

import com.gufli.pathfinding.path.PathExecutor;
import com.gufli.pathfinding.pathfinder.math.Vector;

import java.util.List;

public interface PathPoint {
    /**
     * Returns a new PathPoint at a given Vector.
     */
    PathPoint createAtOffset(Vector vector);

    /**
     * Gets the parent PathPoint
     */
    PathPoint parentPoint();

    /**
     * Gets the list of manual path vectors
     *
     * @see #setPathVectors(List)
     */
    List<Vector> pathVectors();

    /**
     * Sets the path vectors that will be used at pathfinding time. For example, setting a list of vectors to path
     * through in order to reach this pathpoint.
     */
    void setPathVectors(List<Vector> vectors);

    /**
     * Gets the vector represented by this point
     */
    Vector vector();

    /**
     * Sets the vector location of this point
     */
    void setVector(Vector vector);

    void addExecutor(PathExecutor executor);

}
