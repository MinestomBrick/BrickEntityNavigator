package org.minestombrick.entitynavigator.common.astar;

import com.google.common.collect.Lists;
import org.minestombrick.entitynavigator.common.path.Path;
import org.minestombrick.entitynavigator.common.pathfinder.examiner.BlockExaminer;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class AStarNode implements Comparable<AStarNode> {

    protected float g, h;
    protected AStarNode parent;
    protected List<AStarNode> parents;

    protected AStarNode(AStarNode parent) {
        this.parent = parent;
    }

    public abstract Path buildPath();

    @Override
    public int compareTo(AStarNode other) {
        return Float.compare(g + h, other.g + other.h);
    }

    @Override
    public abstract boolean equals(Object other);

    public abstract Iterable<AStarNode> neighbours(Set<BlockExaminer<?>> examiners);

    protected AStarNode parent() {
        return parent;
    }

    @SuppressWarnings("unchecked")
    protected <T extends AStarNode> Iterable<T> parents() {
        if (parents != null)
            return (Iterable<T>) parents;
        parents = Lists.newArrayList();
        AStarNode start = this;
        while (start != null) {
            parents.add(start);
            start = start.parent;
        }
        Collections.reverse(parents);
        return (Iterable<T>) parents;
    }

    protected float totalCost() {
        return g + h;
    }

    @Override
    public abstract int hashCode();
}
