package farn.another_alpha_mod.mixin.accessor;

import net.minecraft.client.gui.screen.inventory.menu.InventoryMenuScreen;
import net.minecraft.inventory.slot.InventorySlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(InventoryMenuScreen.class)
public interface InventoryScreenAccessor {

	@Invoker("getHoveredSlot")
	InventorySlot invokeGetHoveredSlot(int mouseX, int mouseY);
}
