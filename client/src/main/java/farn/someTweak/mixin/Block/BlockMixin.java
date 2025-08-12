package farn.someTweak.mixin.Block;

import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Block.class)
public abstract class BlockMixin {

	@Inject(method = "getBaseDropCount", at = @At("HEAD"), cancellable = true)
	private void fixDoubleSlabDrop(Random random, CallbackInfoReturnable<Integer> cir) {
		if ((Object) this == Block.DOUBLE_STONE_SLAB) {
			cir.setReturnValue(2); // double slab drops 2 singles
		}
	}
}
