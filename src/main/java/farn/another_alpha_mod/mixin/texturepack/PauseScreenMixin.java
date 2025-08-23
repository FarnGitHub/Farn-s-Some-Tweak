package farn.another_alpha_mod.mixin.texturepack;

import farn.another_alpha_mod.AnotherAlphaMod;
import farn.another_alpha_mod.mixin.accessor.MinecraftAccessor;
import farn.another_alpha_mod.mixin.accessor.ScreenAccessor;
import farn.another_alpha_mod.sub_mod.texturepack.GuiTexturePacks;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public class PauseScreenMixin {
	GameMenuScreen gameMenuScreen = ((GameMenuScreen)(Object)this);

	@Inject(method = "init", at = @At("TAIL"), cancellable = true)
	public void init(CallbackInfo ci) {
		((ScreenAccessor)gameMenuScreen).getButtonsList().add(new ButtonWidget(100, gameMenuScreen.width / 2 - 100, gameMenuScreen.height / 4 + 72, "Texture Pack"));
	}

	@Inject(method = "buttonClicked", at = @At("TAIL"), cancellable = true)
	public void buttonClicked(ButtonWidget buttons, CallbackInfo ci) {
		if(buttons.id == 100) {
			AnotherAlphaMod.mc.openScreen(new GuiTexturePacks(gameMenuScreen));
		}

	}
}
