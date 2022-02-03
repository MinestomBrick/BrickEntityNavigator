package com.gufli.pathfinding.pathfinder;

import com.google.common.collect.Lists;
import com.gufli.pathfinding.path.Agent;
import com.gufli.pathfinding.path.Path;
import com.gufli.pathfinding.path.PathExecutor;
import com.gufli.pathfinding.pathfinder.math.Vector;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class VectorPath implements Path {

    private int index = 0;
    private final PathEntry[] path;

    private final float leeway = 0.2f;

    public VectorPath(Collection<Vector> vectors) {
        this.path = vectors.stream().map(vector -> new PathEntry(vector, Collections.emptyList())).toArray(PathEntry[]::new);
    }

    public VectorPath(Iterable<VectorNode> unfiltered) {
        this.path = cull(unfiltered);
    }

    private PathEntry[] cull(Iterable<VectorNode> unfiltered) {
        // possibly expose cullability in an API
        List<PathEntry> path = Lists.newArrayList();
        for (VectorNode node : unfiltered) {
            for (Vector vector : node.pathVectors()) {
                path.add(new PathEntry(vector, node.executors));
            }
        }
        return path.toArray(new PathEntry[0]);
    }

    public Vector currentVector() {
        return path[index].vector;
    }

    public Iterable<Vector> path() {
        return Arrays.stream(path).map(input -> input.vector).collect(Collectors.toList());
    }

    @Override
    public boolean isFinished() {
        return index >= path.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(path);
    }

    @Override
    public void update(Agent agent) {
        if ( isFinished() ) {
            return;
        }

        if ( agent.position().distance(currentVector()) < leeway ) {
            // move to next node if current node is reached
            index++;
            return;
        }

        agent.moveTo(currentVector());
    }

    private record PathEntry(Vector vector, List<PathExecutor> executors) {
        @Override
        public String toString() {
            return vector.toString();
        }
    }

}