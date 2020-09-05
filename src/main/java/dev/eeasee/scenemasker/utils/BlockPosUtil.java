package dev.eeasee.scenemasker.utils;

import com.google.common.collect.AbstractIterator;
import net.minecraft.util.CuboidBlockIterator;
import net.minecraft.util.math.BlockPos;

import java.util.Iterator;
import java.util.function.Predicate;

public class BlockPosUtil {
    private static Iterator<BlockPos> iteratorWithPredicate(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Predicate<BlockPos> includePredicate) {
        return new AbstractIterator<BlockPos>() {
            final CuboidBlockIterator iterator = new CuboidBlockIterator(minX, minY, minZ, maxX, maxY, maxZ);
            final BlockPos.Mutable pos = new BlockPos.Mutable();
            BlockPos.Mutable nextRealPos = new BlockPos.Mutable();
            boolean complete;

            {
                if (this.iterator.step()) {
                    this.nextRealPos = new BlockPos.Mutable(this.iterator.getX(), this.iterator.getY(), this.iterator.getZ());
                }
            }

            @Override
            protected BlockPos computeNext() {
                if (this.nextRealPos == null) {
                    return this.endOfData();
                }

                this.pos.set(nextRealPos);
                complete = true;
                while (this.iterator.step()) {
                    this.nextRealPos.set(this.iterator.getX(), this.iterator.getY(), this.iterator.getZ());
                    if (includePredicate.test(this.nextRealPos)) {
                        complete = false;
                        break;
                    }
                }
                if (complete) {
                    this.nextRealPos = null;
                }
                return this.pos;
            }
        };
    }

    public static Iterable<BlockPos> iterableWithPredicate(BlockPos pos1, BlockPos pos2, Predicate includePredicate) {
        return () -> iteratorWithPredicate(
                Math.min(pos1.getX(), pos2.getX()),
                Math.min(pos1.getY(), pos2.getY()),
                Math.min(pos1.getZ(), pos2.getZ()),
                Math.max(pos1.getX(), pos2.getX()),
                Math.max(pos1.getY(), pos2.getY()),
                Math.max(pos1.getZ(), pos2.getZ()),
                includePredicate);
    }

}
