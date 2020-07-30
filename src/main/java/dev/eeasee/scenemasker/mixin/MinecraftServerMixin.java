package dev.eeasee.scenemasker.mixin;

import com.google.gson.JsonElement;
import dev.eeasee.scenemasker.Masker;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.LevelGeneratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Inject(method = "loadWorld", at = @At(value = "RETURN"))
    private void onServerStarted(String name, String serverName, long seed, LevelGeneratorType generatorType, JsonElement generatorSettings, CallbackInfo ci) {
        Masker.onServerStarted();
    }

    @Inject(method = "shutdown", at = @At(value = "RETURN"))
    private void onServerClosed(CallbackInfo ci) {
        Masker.onServerClosed();
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void onServerTicked(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        Masker.onServerTicked();
    }
}
