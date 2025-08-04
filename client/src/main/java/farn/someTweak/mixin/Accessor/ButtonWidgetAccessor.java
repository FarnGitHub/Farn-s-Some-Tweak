package farn.someTweak.mixin.Accessor;

import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ButtonWidget.class)
public interface ButtonWidgetAccessor {

	@Accessor("width")
	void setWidth(int value);

	@Accessor("width")
	int getWidth();

	@Accessor("height")
	void setHeight(int value);

	@Accessor("height")
	int getHeight();
}
