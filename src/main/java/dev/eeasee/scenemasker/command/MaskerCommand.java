package dev.eeasee.scenemasker.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.eeasee.scenemasker.utils.MaskerWorldUtils;
import net.minecraft.command.arguments.BlockPosArgumentType;
import net.minecraft.server.command.CommandSource;
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
                .then(literal("config")
                        .requires(source -> false) //Deprecated command
                        .then(literal("layer")
                                .then(argument("layer applied", IntegerArgumentType.integer(-1, 255))))
                        .then(literal("revert-all")
                                .then(argument("do reverting", BoolArgumentType.bool())))
                        .then(literal("opacity")
                                .then(argument("opacity", FloatArgumentType.floatArg(0, 1))
                                        .suggests(((context, builder) -> CommandSource.suggestMatching(new String[]{"0", "1"}, builder))))))
                .then(literal("config-menu")
                        .executes(MaskerCommand::openConfigMenuCommand))
                .then(literal("revert")
                        .then(literal("range").then(argument("from", BlockPosArgumentType.blockPos())
                                .then(argument("to", BlockPosArgumentType.blockPos())
                                        .executes(MaskerCommand::setBoxedBlocks))))
                        .then(literal("single")
                                .then(argument("pos", BlockPosArgumentType.blockPos())
                                        .executes(MaskerCommand::setSingleBlockCommand))))
                .then(literal("set")
                        .then(argument("value", BoolArgumentType.bool())
                                .then(literal("range").then(argument("from", BlockPosArgumentType.blockPos())
                                        .then(argument("to", BlockPosArgumentType.blockPos())
                                                .executes(MaskerCommand::setBoxedBlocks))))
                                .then(literal("single")
                                        .then(argument("pos", BlockPosArgumentType.blockPos())
                                                .executes(MaskerCommand::setSingleBlockCommand)
                                        ))));

        dispatcher.register(literalArgumentBuilder);
    }

    private static int openConfigMenuCommand(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) {
        return 1;
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

    private static int mainMenu(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) {
        return 1;
    }

}