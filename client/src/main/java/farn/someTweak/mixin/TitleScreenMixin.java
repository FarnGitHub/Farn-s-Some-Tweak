package farn.someTweak.mixin;

import farn.someTweak.FarnSomeTweak;
import farn.someTweak.mixin.Accessor.ButtonWidgetAccessor;
import farn.someTweak.mixin.Accessor.MinecraftAccessor;
import farn.someTweak.mixin.Accessor.ScreenAccessor;
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
		if(!FabricLoader.getInstance().isModLoaded("deawt")) {
			return;
		}

		ButtonWidget theButton = (ButtonWidget) ((ScreenAccessor)this.titleScreen).getButtonsList().get(3);
		((ButtonWidgetAccessor)theButton).setWidth(98);
		((ButtonWidgetAccessor)theButton).setHeight(20);
		ButtonWidget theQuitButton = new ButtonWidget(100, titleScreen.width / 2 + 2, (titleScreen.height / 4 + 48) + 84, "Quit Game...");
		((ButtonWidgetAccessor)theQuitButton).setWidth(98);
		((ButtonWidgetAccessor)theQuitButton).setHeight(20);
		((ScreenAccessor)this.titleScreen).getButtonsList().add(theQuitButton);

	}

	@Inject(method = "buttonClicked", at = @At("TAIL"), cancellable = true)
	public void buttonClicked(ButtonWidget buttons, CallbackInfo ci) {
		if(!FabricLoader.getInstance().isModLoaded("deawt")) {
			return;
		}

		if(buttons.id == 100) {
			((MinecraftAccessor) FarnSomeTweak.mc).setRunning(false);
		}

	}
}
