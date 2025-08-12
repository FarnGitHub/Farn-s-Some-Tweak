package farn.someTweak.mixin.Block;

import net.minecraft.block.LeavesBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin {

	LeavesBlock blockThis = ((LeavesBlock)(Object)this);

	@Overwrite
	public void tick(World world, int x, int y, int z, Random random) {
		if(!world.getMaterial(x, y - 1, z).isSolid()) {
			for(int i = x - 4; i <= x + 4; ++i) {
				for(int j = y - 3; j <= y; ++j) {
					for(int k = z - 4; k <= z + 4; ++k) {
						if(world.getBlock(i, j, k) == 17) {
							return;
						}
					}
				}
			}
			blockThis.dropItems(world, x, y, z, world.getBlockMetadata(x, y, z));
			world.setBlock(x, y, z, 0);
		}
	}
}
