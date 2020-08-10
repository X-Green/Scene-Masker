package dev.eeasee.scenemasker.utils;

import carpet.script.Fluff;
import com.google.common.collect.Lists;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3i;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BoxUtils {
    public static void forEachInBox(BlockBox blockBox, Consumer<Vec3i> consumer) {
        for (int x = blockBox.minX; x <= blockBox.maxX; x++)
            for (int y = blockBox.minY; y <= blockBox.maxY; y++)
                for (int z = blockBox.minZ; z <= blockBox.maxZ; z++)
                    consumer.accept(new Vec3i(x, y, z));
    }

    public static BlockBox shrinkWithStep(BlockBox blockBox, int stepLength) {
        return shrinkWithStep(blockBox, stepLength, false);
    }

    public static BlockBox shrinkWithStep(BlockBox blockBox, int stepLength, boolean exclusive) {
        int x1 = blockBox.minX / stepLength;
        int x2 = blockBox.maxX / stepLength;
        int y1 = blockBox.minY / stepLength;
        int y2 = blockBox.maxY / stepLength;
        int z1 = blockBox.minZ / stepLength;
        int z2 = blockBox.maxZ / stepLength;
        if (exclusive) {
            if (blockBox.minX % stepLength != 0)
                x1++;
            if (blockBox.minY % stepLength != 0)
                y1++;
            if (blockBox.minZ % stepLength != 0)
                z1++;
            if (blockBox.maxX % stepLength != 0)
                x2++;
            if (blockBox.maxY % stepLength != 0)
                y2++;
            if (blockBox.maxZ % stepLength != 0)
                z2++;
        }
        return new BlockBox(x1, y1, z1, x2, y2, z2);
    }

    public static BlockBox expandWithStep(BlockBox blockBox, int stepLength) {
        return new BlockBox(
                blockBox.minX * stepLength,
                blockBox.minY * stepLength,
                blockBox.minZ * stepLength,
                (blockBox.maxX + 1) * stepLength - 1,
                (blockBox.maxY + 1) * stepLength - 1,
                (blockBox.maxZ + 1) * stepLength - 1
        );
    }

    public static boolean insides(BlockBox inner, BlockBox outer) {
        return (inner.minX >= outer.minX) &&
                (inner.minY >= outer.minY) &&
                (inner.minZ >= outer.minZ) &&
                (inner.maxX <= outer.maxX) &&
                (inner.maxY <= outer.maxY) &&
                (inner.maxZ <= outer.minZ);
    }

    public static BlockBox intersection(BlockBox box1, BlockBox box2) {
        int d = Math.max(box1.minX, box2.minX);
        int e = Math.max(box1.minY, box2.minY);
        int f = Math.max(box1.minZ, box2.minZ);
        int g = Math.min(box1.maxX, box2.maxX);
        int h = Math.min(box1.maxY, box2.maxY);
        int i = Math.min(box1.maxZ, box2.maxZ);
        return new BlockBox(d, e, f, g, h, i);
    }

    public static BlockBox union(BlockBox box1, BlockBox box2) {
        int d = Math.min(box1.minX, box2.minX);
        int e = Math.min(box1.minY, box2.minY);
        int f = Math.min(box1.minZ, box2.minZ);
        int g = Math.max(box1.maxX, box2.maxX);
        int h = Math.max(box1.maxY, box2.maxY);
        int i = Math.max(box1.maxZ, box2.maxZ);
        return new BlockBox(d, e, f, g, h, i);
    }

    public static List<BlockBox> divideToBoxes(BlockBox blockBox, int stepLength) {
        BlockBox shrunk = shrinkWithStep(blockBox, stepLength);
        List<BlockBox> boxes = Lists.newArrayList();
        forEachInBox(shrinkWithStep(blockBox, stepLength, false), vec3i -> {
            int x1 = vec3i.getX() * stepLength;
            int y1 = vec3i.getY() * stepLength;
            int z1 = vec3i.getZ() * stepLength;
            BlockBox blockBox1 = new BlockBox(x1, y1, z1, x1 + stepLength - 1, y1 + stepLength - 1, z1 + stepLength - 1);
            boxes.add(intersection(blockBox, blockBox1));
        });
        return boxes;
    }

    public static void consumeAsDividedBoxes(BlockBox outerBox, int stepLength, Fluff.TriConsumer<Vec3i, Boolean, BlockBox> triConsumer) {
        BlockBox shrunk = shrinkWithStep(outerBox, stepLength);
        forEachInBox(shrinkWithStep(outerBox, stepLength, false), vec3i -> {
            int x1 = vec3i.getX() * stepLength;
            int y1 = vec3i.getY() * stepLength;
            int z1 = vec3i.getZ() * stepLength;
            BlockBox innerBox = new BlockBox(x1, y1, z1, x1 + stepLength - 1, y1 + stepLength - 1, z1 + stepLength - 1);
            boolean isFullInnerBox;
            if (!insides(innerBox, outerBox)) {
                innerBox = intersection(innerBox, outerBox);
                isFullInnerBox = false;
            } else {
                isFullInnerBox = true;
            }
            triConsumer.accept(vec3i, isFullInnerBox, innerBox);
        });
    }
}
