package dev.eeasee.scenemasker.network.handler;

import dev.eeasee.scenemasker.network.packet.s2c.IDataS2C;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class ServerNetworkCustomHandler {

    public void sendToPlayer(PlayerEntity player, Identifier ID, IDataS2C data) {
        ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, ID, data.encode());
    }

    public void sendToWatchingPlayers(World world, BlockPos pos, Identifier ID, IDataS2C data) {
        Stream<PlayerEntity> watchingPlayers = PlayerStream.watching(world,pos);
        watchingPlayers.forEach(playerEntity -> sendToPlayer(playerEntity, ID, data));
    }
}
