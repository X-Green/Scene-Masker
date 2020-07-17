package dev.eeasee.scenemasker.masker;

import com.google.common.collect.Sets;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Set;

public class Masker {
    public static final Identifier MASKER_CHANNEL = new Identifier("eeasee_masker");
    public static Set<BlockPos> maskedBlockPosSet = Sets.newHashSet();
}


