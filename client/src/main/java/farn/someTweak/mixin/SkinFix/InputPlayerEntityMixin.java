package farn.someTweak.mixin.SkinFix;

import farn.someTweak.FarnSomeTweak;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Session;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InputPlayerEntity.class)
public abstract class InputPlayerEntityMixin {

	InputPlayerEntity inputPlayer = (InputPlayerEntity) (Object) this;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void onInit(Minecraft minecraft, World world, Session session, CallbackInfo ci) {
		if (session != null && session.username != null) {
			inputPlayer.skin = FarnSomeTweak.skinLink + session.username + (FarnSomeTweak.appendPng ? ".png" : "");
			FarnSomeTweak.mc.worldRenderer.onEntityAdded(inputPlayer);
		}
	}
}
