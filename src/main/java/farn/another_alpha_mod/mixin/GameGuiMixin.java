package farn.another_alpha_mod.mixin;

import farn.another_alpha_mod.AnotherAlphaMod;
import net.minecraft.client.gui.GameGui;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameGui.class)
public class GameGuiMixin {

	@Unique
	private boolean farn$debugEnabled = false;

	@Unique
	private boolean farn$f3PressedLast = false;

	@Redirect(
		method = "render",
		at = @At(
			value = "INVOKE",
			target = "Lorg/lwjgl/input/Keyboard;isKeyDown(I)Z"
		)
	)
	private boolean redirectF3Key(int key) {
		boolean currentlyDown = Keyboard.isKeyDown(Keyboard.KEY_F3);
		if (currentlyDown && !farn$f3PressedLast) {
			farn$debugEnabled = !farn$debugEnabled; // toggle
		}
		farn$f3PressedLast = currentlyDown;

		return farn$debugEnabled;
	}

	@Inject(
		method = "render",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Runtime;freeMemory()J",
			shift = At.Shift.AFTER
		)
	)
	private void writeText(float tickDelta, boolean screenOpen, int mouseX, int mouseY, CallbackInfo ci) {
		// Add your custom debug line after vanilla memory line
		PlayerEntity plr = AnotherAlphaMod.mc.player;
		String coord = "XYZ: " + floor(plr.x) + "/" + floor(plr.y) + "/" + floor(plr.z);
		AnotherAlphaMod.mc.textRenderer.drawWithShadow(coord, 2, 64, 14737632);
		AnotherAlphaMod.mc.textRenderer.drawWithShadow("Facing: " + getPlayerFacing(plr.yaw), 2, 72, 14737632);
		AnotherAlphaMod.mc.textRenderer.drawWithShadow("Light Level: " + AnotherAlphaMod.mc.world.getRawBrightness(MathHelper.floor(plr.x), MathHelper.floor(plr.y) - 1, MathHelper.floor(plr.z)), 2, 80, 14737632);
		AnotherAlphaMod.mc.textRenderer.drawWithShadow("Seed: " + AnotherAlphaMod.mc.world.seed, 2, 96, 14737632);
		AnotherAlphaMod.mc.textRenderer.drawWithShadow("Time: " + getTime(), 2, 104, 14737632);
	}

	private static String floor(double d) {
		return String.format("%.3f", d);
	}

	private static String getPlayerFacing(float rotationYaw) {
		int facing = MathHelper.floor((rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		switch (facing) {
			case 0: return "South (+Z, 0)";
			case 1: return "West (–X, 1)";
			case 2: return "North (–Z, 2)";
			case 3: return "East (+X, 3)";
			default: return "Unknown";
		}
	}

	private static String getTime() {
		float time = (AnotherAlphaMod.mc.world.getTimeOfDay(1.0F) * 24.0F + 12.0F) % 24.0F;
		int hours = (int) Math.floor(time);
		int minutes = (int) Math.floor(time * 60.0F) - hours * 60;
		return String.format("%02d:%02d", hours, minutes);
	}
}
