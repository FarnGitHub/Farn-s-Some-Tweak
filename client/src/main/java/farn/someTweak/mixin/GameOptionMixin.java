package farn.someTweak.mixin;

import farn.someTweak.FarnSomeTweak;
import net.minecraft.client.Minecraft;
import net.minecraft.client.options.GameOptions;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;

@Mixin(GameOptions.class)
public abstract class GameOptionMixin {

	@Shadow
	public boolean fpsLimit;

	@Inject(method = "<init>(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V", at = @At("TAIL"))
	public void init(Minecraft minecraft, File file, CallbackInfo info) {
		Display.setVSyncEnabled(fpsLimit);
	}

	@Inject(method = "translateValue", at = @At("HEAD"), cancellable = true)
	public void getOptionName(int id, CallbackInfoReturnable info) {
		if(id == 7) {
			info.setReturnValue("Vsync: " + (this.fpsLimit ? "ON" : "OFF"));
		}
	}

	@Inject(method = "setValue(II)V", at= @At("TAIL"))
	public void setValue(int i, int j, CallbackInfo info) {
		if (i == 7) {
			Display.setVSyncEnabled(fpsLimit);
		}
	}

}
