package farn.SeedSelector;

import java.io.File;
import java.util.Random;

import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.interaction.SurvivalInteractionManager;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

public class GuiEnterSeed extends Screen {
	public int world;
	private GuiTextField seedTextbox;
	private Screen parentScreen;

	public GuiEnterSeed(int i1, Screen bh2) {
		this.world = i1;
		this.parentScreen = bh2;
	}

	public void init() {
		Keyboard.enableRepeatEvents(true);
		this.buttons.clear();
		this.buttons.add(new ButtonWidgetCustomSize(1, this.width / 2 - 100, this.height / 4 + 84, 100, 20, "Create World"));
		this.buttons.add(new ButtonWidgetCustomSize(2, this.width / 2, this.height / 4 + 84, 100, 20, "Cancel"));
		this.seedTextbox = new GuiTextField(this, this.textRenderer, this.width / 2 - 100, this.height / 16 + 84, 200, 20, "");
	}

	public void removed() {
		Keyboard.enableRepeatEvents(false);
	}

	protected void buttonClicked(ButtonWidget button) {
		if(button.id == 1) {
			long j2 = (new Random()).nextLong();
			if(this.seedTextbox.getText().length() > 0) {
				try {
					long j4 = Long.parseLong(this.seedTextbox.getText());
					if(j4 != 0L) {
						j2 = j4;
					}
				} catch (NumberFormatException numberFormatException6) {
					j2 = (long)this.seedTextbox.getText().hashCode();
				}
			}

			this.minecraft.openScreen((Screen)null);
			this.minecraft.interactionManager = new SurvivalInteractionManager(this.minecraft);
			this.startWorldWithCustomSeed("World" + this.world, j2);
			this.minecraft.openScreen((Screen)null);
		} else if(button.id == 2) {
			this.minecraft.openScreen(this.parentScreen);
		}

	}

	protected void keyPressed(char character, int key) {
		this.seedTextbox.textboxKeyTyped(character, key);
	}

	protected void mouseClicked(int i1, int i2, int i3) {
		super.mouseClicked(i1, i2, i3);
		this.seedTextbox.mouseClicked(i1, i2, i3);
	}

	public void startWorldWithCustomSeed(String string1, long j2) {
		this.minecraft.setWorld((World)null);
		System.gc();
		Minecraft minecraft10004 = this.minecraft;
		World world4 = new World(new File(Minecraft.getRunDirectory(), "saves"), string1, j2);
		if(world4.fresh) {
			this.minecraft.setWorld(world4, "Generating level");
		} else {
			this.minecraft.setWorld(world4, "Loading level");
		}

	}

	public void render(int mouseX, int mouseY, float renderPartialTick) {
		this.renderBackground();
		this.drawCenteredString(this.textRenderer, "Enter Seed", this.width / 2, 60, 10526880);
		this.seedTextbox.drawTextBox();
		super.render(mouseX, mouseY, renderPartialTick);
	}
}
