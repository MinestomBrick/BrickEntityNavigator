package org.minestombrick.entitynavigator.minestom;

import org.minestombrick.entitynavigator.common.pathfinder.math.BoundingBox;
import org.minestombrick.entitynavigator.common.pathfinder.source.BlockSource;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;

public class MinestomBlockSource implements BlockSource<Block> {

    private final Instance instance;

    protected MinestomBlockSource(Instance instance) {
        this.instance = instance;
    }

    @Override
    public Block type(int x, int y, int z) {
        return instance.getBlock(x, y, z);
    }

    @Override
    public boolean isAvailable(int x, int y, int z) {
        return instance.isChunkLoaded(new Vec(x, y, z));
    }

    @Override
    public BoundingBox collisionBox(int x, int y, int z) {
        // default to 1x1x1
        return new BoundingBox(-1, -1, -1, 1, 1, 1);
    }
}
