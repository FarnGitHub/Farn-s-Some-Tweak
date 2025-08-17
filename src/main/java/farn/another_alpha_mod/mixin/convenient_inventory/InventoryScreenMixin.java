package farn.another_alpha_mod.mixin.convenient_inventory;

import farn.another_alpha_mod.AnotherAlphaMod;
import farn.another_alpha_mod.sub_mod.ConvenientInventory;
import net.minecraft.client.gui.screen.inventory.menu.InventoryMenuScreen;
import net.minecraft.inventory.slot.InventorySlot;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InventoryMenuScreen.class)
public abstract class InventoryScreenMixin {

	InventoryMenuScreen theScreen = (InventoryMenuScreen) (Object) this;
	protected int xSize = 176;
	protected int ySize = 166;

	@Shadow
	List<InventorySlot> menu;

	@Shadow
	abstract InventorySlot getHoveredSlot(int mouseX, int mouseY);

	@Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
	public void mouseClicked(int mouseX, int mouseY, int button, CallbackInfo info) {
		if (button == 0 || button == 1) {
			InventorySlot hoveredSlot = this.getHoveredSlot(mouseX, mouseY);
			int guiLeft = (theScreen.width - this.xSize) / 2;
			int guiTop = (theScreen.height - this.ySize) / 2;
			boolean clickedOutside = mouseX < guiLeft || mouseY < guiTop || mouseX >= guiLeft + this.xSize || mouseY >= guiTop + this.ySize;
			int slotIndex = -1;
			if (hoveredSlot != null) {
				slotIndex = menu.indexOf(hoveredSlot);
			}
			if (clickedOutside) {
				slotIndex = -999;
			}

			boolean shiftPressed = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);  // detects shift key

			ConvenientInventory.mod_convenientInventory_handleClickOnSlot(slotIndex, button, clickedOutside, AnotherAlphaMod.mc, this.menu, shiftPressed);

			info.cancel();
		}
	}

}
