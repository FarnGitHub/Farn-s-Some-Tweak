package farn.another_alpha_mod.mixin;

import farn.another_alpha_mod.AnotherAlphaMod;
import net.minecraft.client.Minecraft;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

	@Inject(method = "run", at = @At(
		value = "INVOKE",
		target = "Lorg/lwjgl/opengl/Display;update()V",
		shift = At.Shift.AFTER
	))
	private void afterDisplayUpdate(CallbackInfo ci) {
		int cap = AnotherAlphaMod.fps;
		if (cap > 0) {
			Display.sync(cap); // Much more accurate FPS cap
		}
	}

	@Redirect(method="init", at= @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;create()V"))
	public void depthBufferFix() {
		try {
			PixelFormat pixelformat = new PixelFormat();
			pixelformat = pixelformat.withDepthBits(24);
			Display.create(pixelformat);
		} catch (LWJGLException e) {
			throw new RuntimeException(e);
		}
	}
}
