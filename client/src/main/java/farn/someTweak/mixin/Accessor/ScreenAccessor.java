package farn.someTweak.mixin.Accessor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(Screen.class)
public interface ScreenAccessor {

	@Accessor("buttons")
	abstract List getButtonsList();

	@Accessor("minecraft")
	abstract Minecraft minecraft();
}
