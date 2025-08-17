package farn.another_alpha_mod.mixin.world;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin {

	/**
	 * Prevent the boat from breaking on collisions.
	 */
	@Redirect(
		method = "tick()V",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/entity/vehicle/BoatEntity;collidingHorizontally:Z"
		)
	)
	private boolean disableCollisionBreak(BoatEntity boat) {
		// Return false so collision does NOT trigger removal
		return false;
	}

	/**
	 * Ensure the boat always drops itself when removed or destroyed.
	 */
	@Redirect(
		method = "damage(Lnet/minecraft/entity/Entity;I)Z",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/vehicle/BoatEntity;remove()V"
		)
	)
	private void dropBoatAndRemove(BoatEntity boat) {
		boat.dropItem(Item.BOAT.id, 1, 0.0F);
		boat.remove();
	}

	@Redirect(
		method = "damage(Lnet/minecraft/entity/Entity;I)Z",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/vehicle/BoatEntity;dropItem(IIF)Lnet/minecraft/entity/ItemEntity;"
		)
	)
	private ItemEntity redirectDropItem(BoatEntity boat, int id, int count, float velocity) {
		if (id == Item.BOAT.id) {
			return boat.dropItem(Item.BOAT.id, 1, 0.0F);
		}
		return null;
	}
}
