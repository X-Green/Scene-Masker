package dev.eeasee.scenemasker.mixin;

import dev.eeasee.scenemasker.Masker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "joinWorld", at = @At("RETURN"))
    private void onConnectedToServer(ClientWorld clientWorld, CallbackInfo ci) {
        Masker.onConnectedToServer();
    }

    @Inject(method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("RETURN"))
    private void onDisconnectedToServer(Screen screen, CallbackInfo ci) {
        Masker.onDisconnectedToServer();
    }
}
