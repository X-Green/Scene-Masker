package dev.eeasee.scenemasker.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.arguments.BlockPosArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandSource.suggestMatching;

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

    private static int setSingleBlockCommand(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) {
        return 1;
    }

    private static int setRangedBlocks(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) {
        return 1;
    }

    private static int revertSingleBlockCommand(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) {
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
    public static void setBlockMaskerState(World world, BlockPos pos, boolean value) {
    }

    public static boolean getBlockMaskerState(World world, BlockPos pos) {
    }

    public static void setRangedBlocksMaskerState(World world, BlockPos pos1, BlockPos pos2, boolean value) {
    }

    public static void revertBlockMaskerState(World world, BlockPos pos) {
    }

}