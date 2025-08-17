package farn.another_alpha_mod.mixin.player.command;

import farn.another_alpha_mod.AnotherAlphaMod;
import farn.another_alpha_mod.unique_getter.InterfaceSPPlayer;
import farn.another_alpha_mod.sub_mod.SPCommand;
import net.minecraft.block.Block;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class EntityPlayerMixin {

	private PlayerEntity playerEntity = (PlayerEntity)(Object)(this);


	@Inject(method = "writeCustomNbt", at=@At("TAIL"))
	public void writeEntityToNBTcustom(NbtCompound nbt, CallbackInfo ci) {
		if(isSPplayer()) {
			nbt.putInt("Score", playerEntity.playerScore);
			getCommand().func_15003_crc_entry(AnotherAlphaMod.mc.world.dir);
		}
	}

	@Inject(method = "readCustomNbt", at=@At("TAIL"))
	public void readEntityFromNBTcustom(NbtCompound compoundTag, CallbackInfo ci) {
		if(isSPplayer()) {
			playerEntity.playerScore = compoundTag.getInt("Score");
			getCommand().func_15005_readWaypointsFromNBT(AnotherAlphaMod.mc.world.dir);
		}
	}

	@Inject(method = "postSpawn", at=@At("TAIL"))
	public void postSpawncustom(CallbackInfo info) {
		if(isSPplayer()) getCommand().func_15005_readWaypointsFromNBT(AnotherAlphaMod.mc.world.dir);
	}

	@Inject(method = "getMiningSpeed", at=@At("HEAD"), cancellable = true)
	public void getMiningSpeedcustom(Block block, CallbackInfoReturnable<Float> cir) {
		if(isSPplayer()) {
			if(getCommand().instamine) cir.setReturnValue(3.402823E38F);
		}
	}

	@Inject(method = "canBreakBlock", at=@At("HEAD"), cancellable = true)
	public void canHarvestBlockcustom(Block block, CallbackInfoReturnable<Boolean> cir) {
		if(isSPplayer()) {
			if(getCommand().instamine) cir.setReturnValue(true);
		}
	}

	private boolean isSPplayer() {
		return this.playerEntity instanceof InputPlayerEntity;
	}

	private SPCommand getCommand() {
		return ((InterfaceSPPlayer)((InputPlayerEntity)playerEntity)).getCommand();
	}
}
