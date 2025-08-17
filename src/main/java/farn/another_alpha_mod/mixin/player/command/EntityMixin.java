package farn.another_alpha_mod.mixin.player.command;

import farn.another_alpha_mod.unique_getter.InterfaceSPPlayer;
import farn.another_alpha_mod.sub_mod.SPCommand;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

	private Entity self = (Entity)(Object)this;

	@Shadow
	private boolean noClip;

	@Shadow
	protected World world;

	@Inject(method = "updateVelocity", at = @At("HEAD"), cancellable = true)
	private void tweakUpdateVelocity(float moveX, float moveZ, float moveY, CallbackInfo cir) {
		if (self instanceof InputPlayerEntity) {
			float f4 = MathHelper.sqrt(moveX * moveX + moveZ * moveZ);
			if(f4 >= 0.01F) {
				if(f4 < 1.0F) {
					f4 = 1.0F;
				}

				f4 = moveY / f4;
				moveX *= f4;
				moveZ *= f4;
				float f5 = MathHelper.sin(self.yaw * 3.141593F / 180.0F);
				float f6 = MathHelper.cos(self.yaw * 3.141593F / 180.0F);
				self.velocityX += (double)((moveX * f6 - moveZ * f5) * getCommand().speed);
				self.velocityZ += (double)((moveZ * f6 + moveX * f5) * getCommand().speed);
			}
			cir.cancel();
		}
	}

	@Inject(method = "isInLava", at=@At("HEAD"), cancellable = true)
	public void isInLava(CallbackInfoReturnable cir) {
		ignoreWhenFLY(cir);
	}

	@Inject(method = "checkWaterCollisions", at=@At("HEAD"), cancellable = true)
	public void isInWater(CallbackInfoReturnable cir) {
		ignoreWhenFLY(cir);
	}

	@Inject(method = "isSubmergedIn", at=@At("HEAD"), cancellable = true)
	public void isSubmergedIn(CallbackInfoReturnable cir) {
		ignoreWhenFLY(cir);
	}

	@Inject(method = "isInWall", at=@At("HEAD"), cancellable = true)
	public void inWall(CallbackInfoReturnable cir) {
		ignoreWhenFLY(cir);
	}

	private void ignoreWhenFLY(CallbackInfoReturnable cir) {
		if(self instanceof InputPlayerEntity) {
			if(noClip || getCommand().fly) {
				cir.setReturnValue(false);;
			}
		}
	}

	private SPCommand getCommand() {
		return ((InterfaceSPPlayer)((InputPlayerEntity)self)).getCommand();
	}
}
