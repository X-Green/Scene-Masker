package dev.eeasee.test;

import dev.eeasee.scenemasker.utils.BlockPosUtil;
import net.minecraft.util.math.BlockPos;

public class Test {
    public static void main(String[] args) {
        BlockPos pos1 = new BlockPos(1, 1, 1);
        BlockPos pos2 = new BlockPos(3, 3, 3);
        BlockPos.iterate(pos1, pos2).forEach(
                System.out::println
        );
        System.out.println("=============");
        BlockPosUtil.iterableWithPredicate(pos1, pos2, (b)-> true).forEach(
                System.out::println
        );
    }
}
