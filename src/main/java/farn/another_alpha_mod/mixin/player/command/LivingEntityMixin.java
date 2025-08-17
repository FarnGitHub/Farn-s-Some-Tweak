package farn.another_alpha_mod.mixin.player.command;

import farn.another_alpha_mod.unique_getter.InterfaceSPPlayer;
import farn.another_alpha_mod.sub_mod.SPCommand;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	private LivingEntity self = (LivingEntity)(Object)this;

	@Redirect(method = "applyFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/LivingEntity;damage(Lnet/minecraft/entity/Entity;I)Z"))
	public boolean stopFallDamageWhileFlying(LivingEntity instance, Entity entity, int i) {
		if(instance instanceof InputPlayerEntity) {
			return !self.noClip && !getCommand().fly ? self.damage(entity, i) : false;
		} else {
			return self.damage(entity, i);
		}
	}

	private SPCommand getCommand() {
		return ((InterfaceSPPlayer)((InputPlayerEntity)self)).getCommand();
	}
}
