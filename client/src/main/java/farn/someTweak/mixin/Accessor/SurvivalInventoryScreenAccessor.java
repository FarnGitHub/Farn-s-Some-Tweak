package farn.someTweak.mixin.Accessor;

import net.minecraft.client.gui.screen.inventory.menu.InventoryMenuScreen;
import net.minecraft.client.gui.screen.inventory.menu.SurvivalInventoryScreen;
import net.minecraft.inventory.slot.InventorySlot;
import net.minecraft.unmapped.C_8593502;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SurvivalInventoryScreen.class)
public interface SurvivalInventoryScreenAccessor {


	@Accessor("f_0751789")
	public C_8593502 getCraftingSlot();
}
