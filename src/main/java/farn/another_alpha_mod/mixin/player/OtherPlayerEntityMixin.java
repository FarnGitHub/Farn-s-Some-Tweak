package farn.another_alpha_mod.mixin.player;

import farn.another_alpha_mod.AnotherAlphaMod;
import net.minecraft.client.entity.living.player.RemotePlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RemotePlayerEntity.class)
public abstract class OtherPlayerEntityMixin {

	RemotePlayerEntity inputPlayer = (RemotePlayerEntity) (Object) this;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void onInit(World world, String name, CallbackInfo ci) {
		if (name != null && name.length() > 0) {
			inputPlayer.skin = AnotherAlphaMod.skinLink + name + (AnotherAlphaMod.appendPng ? ".png" : "");
			AnotherAlphaMod.mc.worldRenderer.onEntityAdded(inputPlayer);
		}
	}
}
