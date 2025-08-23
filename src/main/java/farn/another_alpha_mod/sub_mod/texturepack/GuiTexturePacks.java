package farn.another_alpha_mod.sub_mod.texturepack;


import java.io.File;

import farn.another_alpha_mod.AnotherAlphaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionButtonWidget;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

public class GuiTexturePacks extends Screen {
	protected Screen guiScreen;
	private int field_6454_o = -1;
	private String fileLocation = "";
	private GuiTexturePackSlot guiTexturePackSlot;

	public GuiTexturePacks(Screen guiScreen1) {
		this.guiScreen = guiScreen1;
	}

	public void init() {
		this.buttons.add(new OptionButtonWidget(5, this.width / 2 - 154, this.height - 48, "Open texture pack folder"));
		this.buttons.add(new OptionButtonWidget(6, this.width / 2 + 4, this.height - 48, "Done"));
		AnotherAlphaMod.texturePackList.updateAvaliableTexturePacks();
		this.fileLocation = (new File(Minecraft.getRunDirectory(), "texturepacks")).getAbsolutePath();
		this.guiTexturePackSlot = new GuiTexturePackSlot(this);
		this.guiTexturePackSlot.registerScrollButtons(this.buttons, 7, 8);
	}

	@Override
	public void removed() {
		super.removed();
	}

	protected void buttonClicked(ButtonWidget button) {
		if(button.active) {
			if(button.id == 5) {
				Sys.openURL("file://" + this.fileLocation);
			} else if(button.id == 6) {
				this.minecraft.textureManager.reload();
				this.minecraft.openScreen(this.guiScreen);
			} else {
				this.guiTexturePackSlot.actionPerformed(button);
			}
		}

	}

	protected void mouseClicked(int i1, int i2, int i3) {
		super.mouseClicked(i1, i2, i3);
	}

	protected void mouseReleased(int i1, int i2, int i3) {
		super.mouseReleased(i1, i2, i3);
	}

	public void render(int mouseX, int mouseY, float renderPartialTick) {
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT | GL11.GL_COLOR_BUFFER_BIT);

		this.guiTexturePackSlot.drawScreen(mouseX, mouseY, renderPartialTick);
		if(this.field_6454_o <= 0) {
			AnotherAlphaMod.texturePackList.updateAvaliableTexturePacks();
			this.field_6454_o += 20;
		}

		this.drawCenteredString(this.textRenderer, "Select Texture Pack", this.width / 2, 16, 0xFFFFFF);
		this.drawCenteredString(this.textRenderer, "(Place texture pack files here)", this.width / 2 - 77, this.height - 26, 8421504);
		super.render(mouseX, mouseY, renderPartialTick);
		GL11.glPopAttrib();
	}

	public void tick() {
		super.tick();
		--this.field_6454_o;
	}

	Minecraft getMinecraft() {
		return this.minecraft;
	}
}
