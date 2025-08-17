package farn.another_alpha_mod;

import org.lwjgl.input.Keyboard;

import java.io.*;
import java.util.Properties;

public class AnotherAlphaConfig {

	private File cfgfile;
	private final Properties prop = new Properties();
	public static final AnotherAlphaConfig instance = new AnotherAlphaConfig();

	private AnotherAlphaConfig() {
		System.out.println("init AnotherAlphaConfig");
	}

	public void loadOption() {
		if(!cfgfile.exists()) {
			createOrSaveFile();
		}
		try (FileInputStream in = new FileInputStream(cfgfile)) {
			prop.load(in);
			AnotherAlphaMod.skinLink = prop.getProperty("skinLink", AnotherAlphaMod.skinLink);
			AnotherAlphaMod.appendPng = Boolean.parseBoolean(prop.getProperty("appendPng",  String.valueOf(AnotherAlphaMod.appendPng)));
			AnotherAlphaMod.screenShotKey = Keyboard.getKeyIndex(prop.getProperty("screenShotKey",  Keyboard.getKeyName(AnotherAlphaMod.screenShotKey)));
			AnotherAlphaMod.fps = Integer.parseInt(prop.getProperty("fps", String.valueOf(AnotherAlphaMod.fps)));
			AnotherAlphaMod.vsyncEnabled = Boolean.parseBoolean(prop.getProperty("vsync", String.valueOf(AnotherAlphaMod.vsyncEnabled)));
		} catch (IOException e) {
			System.out.println("Failed to load AnotherAlphaMod.cfg");
			e.printStackTrace();
		}
	}

	public void createOrSaveFile() {
		try {
			PrintWriter printWriter1 = new PrintWriter(new FileWriter(this.cfgfile));
			printWriter1.println("skinLink=" + AnotherAlphaMod.skinLink);
			printWriter1.println("appendPng=" + AnotherAlphaMod.appendPng);
			printWriter1.println("screenShotKey=" + Keyboard.getKeyName(AnotherAlphaMod.screenShotKey));
			printWriter1.println("fps=" + AnotherAlphaMod.fps);
			printWriter1.println("vsync=" + AnotherAlphaMod.vsyncEnabled);
			printWriter1.close();
		} catch (Exception exception3) {
			exception3.printStackTrace();
		}

	}

	public void init() {
		this.cfgfile = new File(AnotherAlphaMod.mc.getRunDirectory(), "AnotherAlphaMod.cfg");
		loadOption();
	}
}
