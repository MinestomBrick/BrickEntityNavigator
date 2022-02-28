package org.minestombrick.entitynavigator.minestom.examiners;

import org.minestombrick.entitynavigator.minestom.MinestomAgent;
import org.minestombrick.entitynavigator.minestom.MinestomBlockSource;
import org.minestombrick.entitynavigator.common.path.Agent;
import org.minestombrick.entitynavigator.common.path.PathExecutor;
import org.minestombrick.entitynavigator.common.pathfinder.math.Vector;
import org.minestombrick.entitynavigator.common.pathfinder.path.PathPoint;
import net.minestom.server.instance.block.Block;

import java.util.ListIterator;

public class MinecraftBlockExaminer extends MinestomExaminer {

    public MinecraftBlockExaminer(MinestomBlockSource blockSource) {
        super(blockSource);
    }

    @Override
    public float cost(PathPoint point) {
        Vector pos = point.vector();
        if ( !blockSource.isAvailable(pos.blockX(), pos.blockY(), pos.blockZ()) ) {
            return 10;
        }

        Block above = blockSource.type(pos.blockX(), pos.blockY() + 1, pos.blockZ());
        Block below = blockSource.type(pos.blockX(), pos.blockY() - 1, pos.blockZ());
        Block in = blockSource.type(pos);

        if (above == Block.COBWEB || in == Block.COBWEB) {
            return 1F;
        }

        if (below == Block.SOUL_SAND || below == Block.ICE) {
            return 1F;
        }

        if ( in == Block.LAVA ) {
            return 2F;
        }

        if ( in.isLiquid() ) { // TODO in liquid (kelp...)
            return 1F;
        }

        return 0F; // TODO: add light level-specific costs?
    }

    @Override
    public PassableState isPassable(PathPoint point) {
        Vector pos = point.vector();
        if ( !blockSource.isAvailable(pos.blockX(), pos.blockY(), pos.blockZ()) ) {
            return PassableState.IGNORE;
        }

        Block in = blockSource.type(pos);

        if ( !canStandIn(in) ) {
            return PassableState.UNPASSABLE;
        }

        Block above = blockSource.type(pos.blockX(), pos.blockY() + 1, pos.blockZ());
        Block below = blockSource.type(pos.blockX(), pos.blockY() - 1, pos.blockZ());

        if ( in.isLiquid() || below.isLiquid() ) {
            return PassableState.UNPASSABLE;
        }

        if ( !canStandOn(below) ) {
            return PassableState.UNPASSABLE;
        }

        if ( above.isSolid() ) { // TODO check for small and big entities through hitbox
            return PassableState.UNPASSABLE;
        }

//        boolean canStand = canStandOn(below) || in.isLiquid() || below.isLiquid() || isClimbable(below);
//        if (!canStand) {
//            return PassableState.UNPASSABLE;
//        }
//
//        if ( isClimbable(in) && (isClimbable(above) || isClimbable(below)) ) {
//            point.addExecutor(new LadderClimber());
//        } else if ( !canStandIn(above) || !canStandIn(in) ) {
//            return PassableState.UNPASSABLE;
//        }
//
//        if (!canJumpOn(below)) {
//            if (point.parentPoint() == null) {
//                return PassableState.UNPASSABLE;
//            }
//
//            Vector parentPos = point.parentPoint().vector();
//            if ((parentPos.x() != pos.x() || parentPos.z() != pos.z())
//                    && pos.sub(point.parentPoint().vector()).y() == 1) {
//                return PassableState.UNPASSABLE;
//            }
//        }

        return PassableState.PASSABLE;
    }

    private static class LadderClimber implements PathExecutor {

        @Override
        public void run(Agent agent, Vector node, ListIterator<Vector> remainingPath) {
            MinestomAgent ma = (MinestomAgent) agent;

            // TODO
        }
    }

    private static boolean canJumpOn(Block mat) {
        return mat.name().contains("FENCE");
    }

    private static boolean canStandIn(Block... mat) {
        boolean passable = true;
        for (Block m : mat) {
            passable &= !m.isSolid();
        }
        return passable;
    }

    // TODO
//    public static boolean canStandOn(Point pos) {
//        Block up = block.getRelative(BlockFace.UP);
//        return canStandOn(block.getType()) && canStandIn(up.getType())
//                && canStandIn(up.getRelative(BlockFace.UP).getType());
//    }

    private static boolean canStandOn(Block type) {
        return type.isSolid() && type != Block.CACTUS;
    }

    private static boolean isDoor(Block in) {
        return in.name().contains("DOOR") && !in.name().contains("TRAPDOOR");
    }

    private static boolean isGate(Block in) {
        return in.name().contains("GATE") && !in.name().contains("GATEWAY");
    }

    private static boolean isClimbable(Block mat) {
        return mat == Block.LADDER || mat == Block.VINE;
    }

}
