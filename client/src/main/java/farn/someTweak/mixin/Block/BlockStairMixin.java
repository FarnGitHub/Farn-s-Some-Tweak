package farn.someTweak.mixin.Block;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(StairsBlock.class)
public abstract class BlockStairMixin {

	@Shadow
	public abstract int getBaseDropCount(Random random);

	public StairsBlock thisStair = (StairsBlock) (Object) this;

	@Overwrite
	public int getDropItem(int metadata, Random random) {
		return thisStair.id;
	}

	@Overwrite
	public void dropItems(World world, int x, int y, int z, int metadata, float luck) {
		if (!world.isMultiplayer) {
			int i = this.getBaseDropCount(world.random);

			for (int j = 0; j < i; j++) {
				if (!(world.random.nextFloat() > luck)) {
					int k = this.getDropItem(metadata, world.random);
					if (k > 0) {
						float f10 = 0.7F;
						double d = world.random.nextFloat() * f10 + (1.0F - f10) * 0.5;
						double e = world.random.nextFloat() * f10 + (1.0F - f10) * 0.5;
						double f13 = world.random.nextFloat() * f10 + (1.0F - f10) * 0.5;
						ItemEntity itemEntity = new ItemEntity(world, x + d, y + e, z + f13, new ItemStack(k));
						itemEntity.pickUpDelay = 10;
						world.addEntity(itemEntity);
					}
				}
			}
		}
	}

	@Overwrite
	public void dropItems(World world, int x, int y, int z, int metadata) {
		this.dropItems(world, x, y, z, metadata, 1.0F);
	}
}
