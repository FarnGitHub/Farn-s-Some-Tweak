package farn.another_alpha_mod.mixin;

import farn.another_alpha_mod.AnotherAlphaMod;
import farn.another_alpha_mod.mixin.accessor.ButtonWidgetAccessor;
import farn.another_alpha_mod.mixin.accessor.MinecraftAccessor;
import farn.another_alpha_mod.mixin.accessor.ScreenAccessor;
import farn.another_alpha_mod.sub_mod.texturepack.GuiTexturePacks;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {

	TitleScreen titleScreen = ((TitleScreen)(Object)this);

	@Inject(method = "init", at = @At("TAIL"), cancellable = true)
	public void init(CallbackInfo ci) {
		ButtonWidget texturePackButton = (ButtonWidget) ((ScreenAccessor)this.titleScreen).getButtonsList().get(2);
		texturePackButton.active = true;
		texturePackButton.message = "Texture Pack";

		if(FabricLoader.getInstance().isModLoaded("deawt")) {
			ButtonWidget theOptionButton = (ButtonWidget) ((ScreenAccessor)this.titleScreen).getButtonsList().get(3);
			((ButtonWidgetAccessor) theOptionButton).setWidth(98);
			((ButtonWidgetAccessor) theOptionButton).setHeight(20);
			ButtonWidget theQuitButton = new ButtonWidget(100, titleScreen.width / 2 + 2, (titleScreen.height / 4 + 48) + 84, "Quit Game...");
			((ButtonWidgetAccessor)theQuitButton).setWidth(98);
			((ButtonWidgetAccessor)theQuitButton).setHeight(20);
			((ScreenAccessor)this.titleScreen).getButtonsList().add(theQuitButton);
		}

	}

	@Inject(method = "buttonClicked", at = @At("TAIL"), cancellable = true)
	public void buttonClicked(ButtonWidget buttons, CallbackInfo ci) {
		if(buttons.id == 3) {
			AnotherAlphaMod.mc.openScreen(new GuiTexturePacks(titleScreen));
		}

		if(FabricLoader.getInstance().isModLoaded("deawt")) {
			if(buttons.id == 100) {
				((MinecraftAccessor) AnotherAlphaMod.mc).setRunning(false);
			}
		}

	}
}
