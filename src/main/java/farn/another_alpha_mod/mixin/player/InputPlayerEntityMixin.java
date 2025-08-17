package farn.another_alpha_mod.mixin.player;

import farn.another_alpha_mod.AnotherAlphaMod;
import farn.another_alpha_mod.unique_getter.InterfaceSPPlayer;
import farn.another_alpha_mod.sub_mod.SPCommand;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Session;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InputPlayerEntity.class)
public abstract class InputPlayerEntityMixin implements InterfaceSPPlayer {

	InputPlayerEntity inputPlayer = (InputPlayerEntity) (Object) this;
	@Unique
	public SPCommand command;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void onInit(Minecraft minecraft, World world, Session session, CallbackInfo ci) {
		if (session != null && session.username != null) {
			inputPlayer.skin = AnotherAlphaMod.skinLink + session.username + (AnotherAlphaMod.appendPng ? ".png" : "");
			AnotherAlphaMod.mc.worldRenderer.onEntityAdded(inputPlayer);
		}
		command = new SPCommand(AnotherAlphaMod.mc, inputPlayer, AnotherAlphaMod.mc.world);
	}

	@Inject(method = "sendChat", at=@At("HEAD"))
	public void sendChat(String content, CallbackInfo info) {
		command.processCommand(content);
	}

	@Inject(method = "tickAi", at=@At("TAIL"))
	public void handleFlying(CallbackInfo info) {
		if(this.command.fly) {
			boolean z1 = false;
			float f2 = inputPlayer.pitch;
			if(this.command.lastkey == AnotherAlphaMod.mc.options.forwardKey.keyCode) {
				f2 = -f2;
				z1 = true;
			} else if(this.command.lastkey == AnotherAlphaMod.mc.options.backKey.keyCode) {
				z1 = true;
			} else {
				if(this.command.lastkey == AnotherAlphaMod.mc.options.jumpKey.keyCode) {
					inputPlayer.velocityY = 1.0D;
					return;
				}

				if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
					inputPlayer.velocityY = -0.6D;
					return;
				}
			}

			inputPlayer.onGround = true;
			if(((double)MathHelper.sqrt(inputPlayer.velocityX * inputPlayer.velocityX) > 0.01D || (double)MathHelper.sqrt(inputPlayer.velocityZ * inputPlayer.velocityZ) > 0.01D) && z1) {
				inputPlayer.velocityY = (double)(f2 / 360.0F * this.command.speed);
			} else {
				inputPlayer.velocityY = 0.0D;
			}
		}

	}

	@Override
	public SPCommand getCommand() {
		return command;
	}
}
