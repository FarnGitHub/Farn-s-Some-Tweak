package farn.another_alpha_mod.mixin.convenient_inventory;

import farn.another_alpha_mod.unique_getter.StackDecreaser;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.slot.InventorySlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(InventorySlot.class)
public abstract class SlotInventoryMixin implements StackDecreaser {

	@Shadow
	Inventory inventory;

	@Shadow
	int slot;

	@Unique
	public ItemStack decrStackSize(int i1) {
		return this.inventory.removeStack(this.slot, i1);
	}



}
