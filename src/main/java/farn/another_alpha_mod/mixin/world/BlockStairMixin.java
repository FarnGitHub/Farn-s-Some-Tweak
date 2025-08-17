package farn.another_alpha_mod.mixin.world;

import net.minecraft.block.StairsBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

@Mixin(StairsBlock.class)
public class BlockStairMixin {

	StairsBlock theBlock = (StairsBlock) (Object) (this);

	@Overwrite
	public int getDropItem(int metadata, Random random) {
		return theBlock.id;
	}

	@Overwrite
	public int getBaseDropCount(Random random) {
		return 1;
	}

	@Overwrite
	public void dropItems(World world, int x, int y, int z, int metadata, float luck) {
		if(!world.isMultiplayer) {
			float f = 0.7F;
			double d = (double)(world.random.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
			double d1 = (double)(world.random.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
			double d2 = (double)(world.random.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
			ItemEntity entityitem = new ItemEntity(world, (double)x + d, (double)y + d1, (double)z + d2, new ItemStack(theBlock.id));
			entityitem.pickUpDelay = 10;
			world.addEntity(entityitem);
		}
	}

	@Overwrite
	public void dropItems(World world, int x, int y, int z, int metadata) {
		((StairsBlock)(Object)this).dropItems(world, x, y, z, metadata);
	}

}
