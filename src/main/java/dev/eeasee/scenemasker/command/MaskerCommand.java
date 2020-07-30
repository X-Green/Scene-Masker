package dev.eeasee.scenemasker.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.eeasee.scenemasker.Masker;
import dev.eeasee.scenemasker.fakes.WorldInterface;
import net.minecraft.command.arguments.BlockPosArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.minecraft.server.command.CommandManager.*;
import static net.minecraft.server.command.CommandSource.*;

public class MaskerCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = literal("masker");
        literalArgumentBuilder = literalArgumentBuilder
                .executes(MaskerCommand::mainMenu)
                .then(literal("mode"))
                .then(literal("block")
                        .then(argument("operation", StringArgumentType.word())
                                .suggests(((context, builder) -> suggestMatching(new String[]{"add", "remove"}, builder)))
                                .then(argument("blockPos", BlockPosArgumentType.blockPos())
                                        .executes(MaskerCommand::operateBlockPos))))
                .then(literal("visual"));

        dispatcher.register(literalArgumentBuilder);
    }

    private static int operateBlockPos(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) throws CommandSyntaxException {
        String operation = StringArgumentType.getString(serverCommandSourceCommandContext, "operation");
        BlockPos blockPos = BlockPosArgumentType.getBlockPos(serverCommandSourceCommandContext, "blockPos");
        World world = serverCommandSourceCommandContext.getSource().getWorld();
        switch (operation) {
            case "add": {
                ((WorldInterface)world).getWorldMasker().setBlockMasked(blockPos, true);
            }
            case "remove": {
                ((WorldInterface)world).getWorldMasker().setBlockMasked(blockPos, true);
            }
        }
        System.out.println(operation);
        System.out.println(blockPos);
        return 1;
    }

    private static int mainMenu(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) {
        return 1;
    }


}