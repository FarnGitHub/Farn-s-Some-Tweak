package farn.someTweak.mixin;

import farn.someTweak.FarnSomeTweak;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

	@Inject(method = "run", at = @At(
		value = "INVOKE",
		target = "Lorg/lwjgl/opengl/Display;update()V",
		shift = At.Shift.AFTER
	))
	private void afterDisplayUpdate(CallbackInfo ci) {
		int cap = FarnSomeTweak.fps;
		if (cap > 0) {
			Display.sync(cap); // Much more accurate FPS cap
		}
	}
}
