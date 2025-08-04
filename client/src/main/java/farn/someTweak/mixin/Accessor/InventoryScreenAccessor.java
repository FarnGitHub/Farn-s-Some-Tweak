package farn.someTweak.mixin.Accessor;

import net.minecraft.client.gui.screen.inventory.menu.InventoryMenuScreen;
import net.minecraft.inventory.slot.InventorySlot;
import net.minecraft.unmapped.C_0350299;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(InventoryMenuScreen.class)
public interface InventoryScreenAccessor {

	@Invoker("getHoveredSlot")
	InventorySlot invokeGetHoveredSlot(int mouseX, int mouseY);
}
