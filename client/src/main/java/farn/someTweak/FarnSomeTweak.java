package farn.someTweak;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.ornithemc.osl.lifecycle.api.MinecraftEvents;
import org.lwjgl.input.Keyboard;

import java.io.*;

public class FarnSomeTweak implements ClientModInitializer {

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.

	public static Minecraft mc;
	boolean isTakingScreenshot = false;
	public static String skinLink = "http://resourceproxy.pymcl.net/skinapi.php?user=";
	public static boolean appendPng = true;
	public static int screenShotKey = Keyboard.KEY_F2;
	private File cfgfile;

	@Override
	public void onInitializeClient() {
		this.cfgfile = new File(mc.getRunDirectory(), "Farn_options.txt");
		loadOption();
		MinecraftEvents.TICK_END.register(minecraft -> {
			if(mc.world != null) {
				this.screenshotListener();
			}
		});

	}

	private void screenshotListener() {
		if(Keyboard.isKeyDown(screenShotKey)) {
			if(!this.isTakingScreenshot) {
				this.isTakingScreenshot = true;
				mc.gui.addChatMessage(ScreenShotHelper.saveScreenshot(mc.getRunDirectory(), mc.width, mc.height));
			}
		} else {
			this.isTakingScreenshot = false;
		}

	}

	public void loadOption() {
		try {
			if (!this.cfgfile.exists()) {
				createFile();
			}

			BufferedReader bufferedReader = new BufferedReader(new FileReader(this.cfgfile));
			String string = "";

			while ((string = bufferedReader.readLine()) != null) {
				String[] strings = string.split("#");
				if (strings[0].equals("skinLink")) {
					this.skinLink = strings[1];
				}

				if (strings[0].equals("sound")) {
					this.appendPng = strings[1].equals("true");
				}

				if (strings[0].equals("screenShotKey")) {
					this.screenShotKey = Keyboard.getKeyIndex(strings[1]);
				}
			}

			bufferedReader.close();
		} catch (Exception var5) {
			var5.printStackTrace();
		}
	}

	public static boolean fc_isShiftPressed() {
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
	}

	public static boolean fc_canStacksMerge(ItemStack itemStack1, ItemStack itemStack2) {
		return itemStack1 != null && itemStack2 != null ? (itemStack1.itemId == itemStack2.itemId && itemStack1.metadata == itemStack2.metadata ? itemStack1.size + itemStack2.size <= itemStack1.getItem().getMaxStackSize() : false) : false;
	}

	public static int fc_getMergeCount(ItemStack itemStack1, ItemStack itemStack2) {
		if(itemStack1 != null && itemStack2 != null) {
			if(itemStack1.itemId == itemStack2.itemId && itemStack1.metadata == itemStack2.metadata) {
				int i3 = itemStack1.getItem().getMaxStackSize() - (itemStack1.size + itemStack2.size);
				return i3 >= 0 ? itemStack1.size : itemStack1.size + i3;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	public void createFile() {
		try {
			PrintWriter printWriter1 = new PrintWriter(new FileWriter(this.cfgfile));
			printWriter1.println("skinLink#" + this.skinLink);
			printWriter1.println("appendPng#" + this.appendPng);
			printWriter1.println("screenShotKey#" + Keyboard.getKeyName(screenShotKey));
			printWriter1.close();
		} catch (Exception exception3) {
			exception3.printStackTrace();
		}

	}

}
