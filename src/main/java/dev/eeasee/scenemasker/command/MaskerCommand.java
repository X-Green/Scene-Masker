package dev.eeasee.scenemasker.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.command.arguments.BlockPosArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static dev.eeasee.scenemasker.utils.MaskerWorldUtils.revertBlockMaskerState;
import static dev.eeasee.scenemasker.utils.MaskerWorldUtils.setBlockMaskerState;
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
                                        .executes(MaskerCommand::revertRangedBlocksCommand)))
                        .then(literal("block")
                                .then(argument("pos", BlockPosArgumentType.blockPos())
                                        .executes(MaskerCommand::revertSingleBlockCommand))))
                .then(literal("set")
                        .then(argument("value", BoolArgumentType.bool()))
                        .then(argument("from", BlockPosArgumentType.blockPos())
                                .then(argument("to", BlockPosArgumentType.blockPos())
                                        .executes(MaskerCommand::setRangedBlocks)))
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
        setBlockMaskerState(world, pos, value);
        return 1;
    }

    private static int setRangedBlocks(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) {
        return 1;
    }

    private static int revertSingleBlockCommand(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) throws CommandSyntaxException {
        World world = serverCommandSourceCommandContext.getSource().getWorld();
        BlockPos pos = BlockPosArgumentType.getBlockPos(serverCommandSourceCommandContext, "pos");
        revertBlockMaskerState(world, pos);
        return 1;
    }

    private static int revertRangedBlocksCommand(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) {
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

    //Utils below
    public static void setRangedBlocksMaskerState(World world, BlockPos pos1, BlockPos pos2, boolean value) {

    }

}