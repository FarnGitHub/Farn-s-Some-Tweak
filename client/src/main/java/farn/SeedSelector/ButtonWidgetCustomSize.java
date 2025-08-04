package farn.SeedSelector;

import net.minecraft.client.gui.widget.ButtonWidget;

public class ButtonWidgetCustomSize extends ButtonWidget {

	public ButtonWidgetCustomSize(int id, int x, int y, int widthyra, int heightyra, String message) {
		super(id, x, y, message);
		this.width = widthyra;
		this.height = heightyra;
	}


}
