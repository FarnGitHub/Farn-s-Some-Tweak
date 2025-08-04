package farn.someTweak.mixin;

import farn.someTweak.FarnSomeTweak;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftGetter {

	@Inject(method = "init", at= @At(value = "NEW", target = "(Lnet/minecraft/client/Minecraft;Ljava/io/File;)Lnet/minecraft/client/options/GameOptions;"))
	public void LoadGame(CallbackInfo info) {
		FarnSomeTweak.mc = (Minecraft)(Object)this;
	}

}
