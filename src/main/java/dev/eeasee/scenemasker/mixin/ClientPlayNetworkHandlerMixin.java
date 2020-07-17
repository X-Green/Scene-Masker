package dev.eeasee.scenemasker.mixin;

import dev.eeasee.scenemasker.network.ClientNetworkHandler;
import dev.eeasee.scenemasker.masker.Masker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin
{
    @Shadow private MinecraftClient client;

    @Inject(method = "onCustomPayload", at = @At("HEAD"), cancellable = true)
    private void onOnCustomPayload(CustomPayloadS2CPacket packet, CallbackInfo ci) {
        if (Masker.MASKER_CHANNEL.equals(packet.getChannel())) {
            ClientNetworkHandler.handleData(packet.getData(), client.player);
            ci.cancel();
        }
    }

    @Inject(method = "onGameJoin", at = @At("RETURN"))
    private void onGameJoined(GameJoinS2CPacket packet, CallbackInfo info) {

    }

    @Inject(method = "onDisconnected", at = @At("HEAD"))
    private void onOnDisconnected(Text reason, CallbackInfo ci) {

    }

}
