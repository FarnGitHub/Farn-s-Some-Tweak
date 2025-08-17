package farn.another_alpha_mod.mixin;

import farn.another_alpha_mod.AnotherAlphaConfig;
import farn.another_alpha_mod.AnotherAlphaMod;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.texture.TextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.objectweb.asm.Opcodes;

@Mixin(GameOptions.class)
public abstract class GameOptionMixin {

	@Shadow
	public boolean fpsLimit;

	@Shadow
	public boolean anaglyph;

	@Redirect(method = "setValue(II)V",
			at = @At(value = "FIELD", target = "Lnet/minecraft/client/options/GameOptions;fpsLimit:Z", opcode = Opcodes.PUTFIELD))
	private void preventAnaglyphToggle(GameOptions instance, boolean value) {
		// Prevent toggle by skipping field write
		anaglyph = false;
	}

	@Redirect(method = "setValue(II)V",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/texture/TextureManager;reload()V", opcode = Opcodes.PUTFIELD))
	private void preventTextureReload(TextureManager instance) {

	}

	@Redirect(method = "setValue(II)V",
		at = @At(value = "FIELD", target = "Lnet/minecraft/client/options/GameOptions;anaglyph:Z", opcode = Opcodes.PUTFIELD))
	private void preventFpsLimitToggle(GameOptions instance, boolean value) {
		// Prevent toggle by skipping field write
		fpsLimit = false;
	}

	@Inject(method = "m_7544135", at = @At("HEAD"), cancellable = true)
	public void getIsFloatOption(int i, CallbackInfoReturnable info) {
		if(i == 6) info.setReturnValue(1);
	}

	@Inject(method = "getValueFloat", at = @At("HEAD"), cancellable = true)
	private void getCustomFloatValue(int optionId, CallbackInfoReturnable<Float> cir) {
		if (optionId == 6) { // your FPS option ID
			cir.setReturnValue((float)(AnotherAlphaMod.fps - 60) / 180f);
			// Normalize between 0.0 and 1.0 because your slider expects float 0..1
			// 60 FPS minimum, 240 FPS max => range 180
		}
	}

	@Inject(method = "translateValue", at = @At("HEAD"), cancellable = true)
	private void getOptionName(int id, CallbackInfoReturnable<String> cir) {
		if (id == 7) {
			cir.setReturnValue("VSync: " + (AnotherAlphaMod.vsyncEnabled ? "ON" : "OFF"));
		} else if(id == 6) {
			//60-240 FPS
			cir.setReturnValue("FPS: " + AnotherAlphaMod.fps);
		}
	}

	@Inject(method = "setValue(II)V", at = @At("HEAD"))
	private void setVsync(int i, int j, CallbackInfo ci) {
		if (i == 7) {
			AnotherAlphaMod.vsyncEnabled = !AnotherAlphaMod.vsyncEnabled; // flip the boolean
			AnotherAlphaMod.requestVsyncToggle(AnotherAlphaMod.vsyncEnabled);
		}
	}

	@Inject(method = "setValue(IF)V", at = @At("HEAD"), cancellable = true)
	private void setFloat(int i, float f, CallbackInfo ci) {
		//f_7496186 IS NUMBER OF OPTIONS
		if (i == 6) {
			int steps = Math.round(f * 36f); // 0 to 36 steps
			int newFps = 60 + steps * 5;     // 60 + steps*5 fps
			AnotherAlphaMod.fps = newFps;
			AnotherAlphaConfig.instance.createOrSaveFile();
			ci.cancel();
		}
	}
}
