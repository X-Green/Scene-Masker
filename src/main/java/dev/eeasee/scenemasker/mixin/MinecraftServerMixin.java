package dev.eeasee.scenemasker.mixin;

import com.google.gson.JsonElement;
import dev.eeasee.scenemasker.Masker;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.LevelGeneratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "loadWorld", at = @At(value = "RETURN"))
    private void onServerStarted(String name, String serverName, long seed, LevelGeneratorType generatorType, JsonElement generatorSettings, CallbackInfo ci) {
        Masker.onServerStarted();
    }

    @Inject(method = "shutdown", at = @At(value = "RETURN"))
    private void onServerClosed(CallbackInfo ci) {
        Masker.onServerClosed();
    }
}
