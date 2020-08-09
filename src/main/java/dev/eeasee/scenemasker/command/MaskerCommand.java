package dev.eeasee.scenemasker.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.eeasee.scenemasker.utils.MaskerWorldUtils;
import net.minecraft.command.arguments.BlockPosArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class MaskerCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = literal("masker");
        literalArgumentBuilder = literalArgumentBuilder
                .executes(MaskerCommand::mainMenu)
                .then(literal("settings")
                        .executes(MaskerCommand::settingsMenu)
                        .then(literal("layer")
                                .then(argument("layer applied", IntegerArgumentType.integer(-1, 255))
                                        .executes(MaskerCommand::changeDisplayLayerCommand)))
                        .then(literal("swap")
                                .executes(MaskerCommand::swapRenderType)))
                .then(literal("revert")
                        .then(argument("from", BlockPosArgumentType.blockPos())
                                .then(argument("to", BlockPosArgumentType.blockPos())
                                        .executes(MaskerCommand::revertBoxedBlocksCommand)))
                        .then(literal("block")
                                .then(argument("pos", BlockPosArgumentType.blockPos())
                                        .executes(MaskerCommand::revertSingleBlockCommand))))
                .then(literal("set")
                        .then(argument("value", BoolArgumentType.bool()))
                        .then(argument("from", BlockPosArgumentType.blockPos())
                                .then(argument("to", BlockPosArgumentType.blockPos())
                                        .executes(MaskerCommand::setBoxedBlocks)))
                        .then(literal("block")
                                .then(argument("pos", BlockPosArgumentType.blockPos())
                                        .executes(MaskerCommand::setSingleBlockCommand)
                                )));


        dispatcher.register(literalArgumentBuilder);
    }

    private static int setSingleBlockCommand(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) throws CommandSyntaxException {
        World world = serverCommandSourceCommandContext.getSource().getWorld();
        boolean value = BoolArgumentType.getBool(serverCommandSourceCommandContext, "value");
        BlockPos pos = BlockPosArgumentType.getBlockPos(serverCommandSourceCommandContext, "pos");
        MaskerWorldUtils.setBlockMaskerState(world, pos, value);
        return 1;
    }

    private static int setBoxedBlocks(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) throws CommandSyntaxException {
        World world = serverCommandSourceCommandContext.getSource().getWorld();
        boolean value = BoolArgumentType.getBool(serverCommandSourceCommandContext, "value");
        BlockPos pos1 = BlockPosArgumentType.getBlockPos(serverCommandSourceCommandContext, "from");
        BlockPos pos2 = BlockPosArgumentType.getBlockPos(serverCommandSourceCommandContext, "to");
        MaskerWorldUtils.setBoxedBlockMaskerStates(world, new BlockBox(pos1, pos2), value);
        return 1;
    }

    private static int revertSingleBlockCommand(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) throws CommandSyntaxException {
        World world = serverCommandSourceCommandContext.getSource().getWorld();
        BlockPos pos = BlockPosArgumentType.getBlockPos(serverCommandSourceCommandContext, "pos");
        MaskerWorldUtils.revertBlockMaskerState(world, pos);
        return 1;
    }

    private static int revertBoxedBlocksCommand(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) throws CommandSyntaxException {
        World world = serverCommandSourceCommandContext.getSource().getWorld();
        BlockPos pos1 = BlockPosArgumentType.getBlockPos(serverCommandSourceCommandContext, "from");
        BlockPos pos2 = BlockPosArgumentType.getBlockPos(serverCommandSourceCommandContext, "to");
        MaskerWorldUtils.revertBoxedBlockMaskerStates(world, new BlockBox(pos1, pos2));
        return 1;
    }

    private static int changeDisplayLayerCommand(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) {
        return 1;
    }

    private static int swapRenderType(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) {
        return 1;
    }

    private static int settingsMenu(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) {
        return 1;
    }

    private static int mainMenu(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) {
        return 1;
    }

}