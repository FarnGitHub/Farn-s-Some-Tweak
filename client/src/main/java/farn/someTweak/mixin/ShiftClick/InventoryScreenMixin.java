package farn.someTweak.mixin.ShiftClick;

import farn.someTweak.ConvenientInventory;
import farn.someTweak.FarnSomeTweak;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.menu.InventoryMenuScreen;
import net.minecraft.inventory.slot.InventorySlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
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
	public void mouseClicked(int i1, int i2, int i3, CallbackInfo info) {
		if(i3 == 0 || i3 == 1) {
			InventorySlot slot4 = this.getHoveredSlot(i1, i2);
			int i5 = (theScreen.width - this.xSize) / 2;
			int i6 = (theScreen.height - this.ySize) / 2;
			boolean z7 = i1 < i5 || i2 < i6 || i1 >= i5 + this.xSize || i2 >= i6 + this.ySize;
			int i8 = -1;
			if(slot4 != null) {
				i8 = menu.indexOf(slot4);
			}

			if(z7) {
				i8 = -999;
			}

			if(i8 != -1) {
				ConvenientInventory.mod_convenientInventory_handleClickOnSlot(i8, i3, z7, FarnSomeTweak.mc, this.menu);
			}
		}
		info.cancel();
	}

}
