package farn.SeedSelector.mixin;

import farn.SeedSelector.GuiEnterSeed;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.world.SelectWorldScreen;

@Mixin(value = SelectWorldScreen.class)
public abstract class SelectWorldMixin extends Screen{


	@Shadow
	protected abstract String getWorldName(int index);

	@Inject(method = "loadWorld", at = @At("HEAD"), cancellable = true)
	public void loadWorldMixin(int id, CallbackInfo ci) {
		if(getWorldName(id) == null) {
			this.minecraft.openScreen(new GuiEnterSeed(id, (SelectWorldScreen)(Object)this));
			ci.cancel();
		}

	}
}
