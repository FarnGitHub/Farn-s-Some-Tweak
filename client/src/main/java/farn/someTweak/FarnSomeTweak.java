package farn.someTweak;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.ornithemc.osl.lifecycle.api.MinecraftEvents;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.io.*;
import java.util.Properties;

public class FarnSomeTweak implements ClientModInitializer {

	public static Minecraft mc;

	public static String skinLink = "http://resourceproxy.pymcl.net/skinapi.php?user=";
	public static boolean appendPng = true;
	public static int screenShotKey = Keyboard.KEY_F2;
	public static int fps = 60;

	private File cfgfile;
	private final Properties prop = new Properties();

	boolean isTakingScreenshot = false;
	private static boolean vsyncToggleRequested = false;
	public static boolean vsyncEnabled;

	private static FarnSomeTweak instance;

	public static void requestVsyncToggle(boolean enabled) {
		vsyncToggleRequested = true;
		vsyncEnabled = enabled;
	}

	@Override
	public void onInitializeClient() {
		MinecraftEvents.START.register(minecraft -> {
			mc = minecraft;
			instance = this;
			this.cfgfile = new File(mc.getRunDirectory(), "Farn_options.cfg");
			loadOption();
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
		if(!cfgfile.exists()) {
			createOrSaveFile();
		}
		try (FileInputStream in = new FileInputStream(cfgfile)) {
			prop.load(in);
			skinLink = prop.getProperty("skinLink", skinLink);
			appendPng = Boolean.parseBoolean(prop.getProperty("appendPng",  String.valueOf(appendPng)));
			screenShotKey = Keyboard.getKeyIndex(prop.getProperty("screenShotKey",  Keyboard.getKeyName(screenShotKey)));
			fps = Integer.parseInt(prop.getProperty("fps", String.valueOf(fps)));
			vsyncEnabled = Boolean.parseBoolean(prop.getProperty("vsync", String.valueOf(vsyncEnabled)));
		} catch (IOException e) {
			System.out.println("Failed to load Farn_options.cfg");
			e.printStackTrace();
		}
	}

	public void createOrSaveFile() {
		try {
			PrintWriter printWriter1 = new PrintWriter(new FileWriter(this.cfgfile));
			printWriter1.println("skinLink=" + skinLink);
			printWriter1.println("appendPng=" + appendPng);
			printWriter1.println("screenShotKey=" + Keyboard.getKeyName(screenShotKey));
			printWriter1.println("fps=" + fps);
			printWriter1.println("vsync=" + vsyncEnabled);
			printWriter1.close();
		} catch (Exception exception3) {
			exception3.printStackTrace();
		}

	}

	public static FarnSomeTweak getInstance() {
		return instance;
	}

}
