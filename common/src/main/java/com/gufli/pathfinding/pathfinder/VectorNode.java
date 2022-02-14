package com.gufli.pathfinding.pathfinder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.gufli.pathfinding.astar.AStarNode;
import com.gufli.pathfinding.path.Path;
import com.gufli.pathfinding.path.PathExecutor;
import com.gufli.pathfinding.pathfinder.examiner.BlockExaminer;
import com.gufli.pathfinding.pathfinder.examiner.NeighbourGeneratorBlockExaminer;
import com.gufli.pathfinding.pathfinder.math.Vector;
import com.gufli.pathfinding.pathfinder.path.PathPoint;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class VectorNode extends AStarNode implements PathPoint {

    protected Vector vector;
    protected List<Vector> pathVectors;
    protected List<PathExecutor> executors;

    // cache
    private Set<BlockExaminer<?>> examinersCache;
    private float cost = -1;

    public VectorNode(Vector vector) {
        this(null, vector);
    }

    public VectorNode(VectorNode parent, Vector vector) {
        super(parent);
        this.vector = vector;
    }

    @Override
    public Path buildPath() {
        return new VectorPath(parents());
    }

    @Override
    public VectorNode createAtOffset(Vector mod) {
        return new VectorNode(this, mod);
    }

    @Override
    public PathPoint parentPoint() {
        return (PathPoint) parent();
    }

    @Override
    public List<Vector> pathVectors() {
        return pathVectors != null ? pathVectors : ImmutableList.of(vector);
    }

    @Override
    public Vector vector() {
        return vector;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        return prime + ((vector == null) ? 0 : vector.hashCode());
    }

    @Override
    public void setPathVectors(List<Vector> vectors) {
        this.pathVectors = vectors;
    }

    @Override
    public void setVector(Vector vector) {
        this.vector = vector;
    }

    @Override
    public void addExecutor(PathExecutor executor) {
        executors.add(executor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        VectorNode other = (VectorNode) obj;
        if (vector == null) {
            return other.vector == null;
        }

        return vector.equals(other.vector);
    }

    public float heuristic(Vector goal) {
        return (float) (vector.distance(goal) + blockCost()) * TIEBREAKER;
    }

    @Override
    public Iterable<AStarNode> neighbours(Set<BlockExaminer<?>> examiners) {
        this.examinersCache = examiners;
        Set<PathPoint> neighbours = new HashSet<>();
        for (BlockExaminer<?> examiner : examiners) {
            if ( examiner instanceof NeighbourGeneratorBlockExaminer exam ) {
                neighbours.addAll(exam.neighbours(this));
            }
        }

        if ( neighbours.isEmpty() ) {
            neighbours = new HashSet<>(neighbours(this));
        }

        return neighbours.stream().filter(this::isPassable)
                .map(point -> (AStarNode) point)
                .collect(Collectors.toList());
    }

    private List<PathPoint> neighbours(PathPoint point) {
        List<PathPoint> neighbours = Lists.newArrayList();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if ( x == 0 && y == 0 && z == 0 ) {
                        continue;
                    }

                    int modX = vector.blockX() + x;
                    int modY = vector.blockY() + y;
                    int modZ = vector.blockZ() + z;

                    Vector mod = new Vector(modX, modY, modZ);

                    // diagonal check
                    if ( x != 0 && z != 0 ) {
                        if ( !isPassable(point.createAtOffset(new Vector(modX, modY, vector.z())))
                                || !isPassable(point.createAtOffset(new Vector(vector.x(), modY, modZ)))) {
                            continue;
                        }
                    }

                    neighbours.add(point.createAtOffset(mod));
                }
            }
        }
        return neighbours;
    }

    private boolean isPassable(PathPoint mod) {
        boolean passable = false;
        for (BlockExaminer<?> examiner : examinersCache) {
            BlockExaminer.PassableState state = examiner.isPassable(mod);
            if (state == BlockExaminer.PassableState.IGNORE)
                continue;
            passable = state == BlockExaminer.PassableState.PASSABLE;
        }
        return passable;
    }

    private float blockCost() {
        if ( cost != -1 || examinersCache == null ) {
            return cost;
        }

        cost = 0;
        for ( BlockExaminer<?> examiner : examinersCache) {
            cost += examiner.cost(this);
        }
        return cost;
    }

    private static final float TIEBREAKER = 1.001f;

}