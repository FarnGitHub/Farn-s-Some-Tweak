package farn.another_alpha_mod;

import farn.another_alpha_mod.sub_mod.ScreenShotHelper;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.ornithemc.osl.lifecycle.api.MinecraftEvents;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class AnotherAlphaMod implements ClientModInitializer {

	public static Minecraft mc;

	public static String skinLink = "http://resourceproxy.pymcl.net/skinapi.php?user=";
	public static boolean appendPng = true;
	public static int screenShotKey = Keyboard.KEY_F2;
	public static int fps = 60;

	public static boolean stairAndSlabRecipeTweak = true;
	public static boolean stairAndSlabProperDrop = true;
	public static boolean betterBoat = true;

	private static boolean vsyncToggleRequested = false;
	public static boolean vsyncEnabled;

	@Override
	public void onInitializeClient() {
		MinecraftEvents.START.register(minecraft -> {
			mc = minecraft;
			AnotherAlphaConfig.instance.init();
		});

		MinecraftEvents.READY.register(minecraft -> {
			Display.setVSyncEnabled(vsyncEnabled);
		});

		MinecraftEvents.TICK_START.register(minecraft -> {
			if (vsyncToggleRequested) {
				Display.setVSyncEnabled(vsyncEnabled);
				vsyncToggleRequested = false;
			}

			if(mc.world != null) {
				ScreenShotHelper.screenshotListener(minecraft);
				if (!minecraft.isMultiplayer() &&  minecraft.screen == null && Keyboard.getEventKey() == minecraft.options.chatKey.keyCode) {
					minecraft.openScreen(new ChatScreen());
				}
			}
		});

	}

	public static void requestVsyncToggle(boolean enabled) {
		vsyncToggleRequested = true;
		vsyncEnabled = enabled;
	}


}
