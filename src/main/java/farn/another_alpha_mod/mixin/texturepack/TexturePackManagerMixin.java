package farn.another_alpha_mod.mixin.texturepack;

import farn.another_alpha_mod.AnotherAlphaMod;
import net.minecraft.client.render.texture.TextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

@Mixin(TextureManager.class)
public class TexturePackManagerMixin {

	@Redirect(
		method = {"load", "reload"},
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Class;getResourceAsStream(Ljava/lang/String;)Ljava/io/InputStream;"
		)
	)
	private InputStream redirectGetResourceStream(Class<?> clazz, String path) {
		// Try loading from selected texture pack first
		InputStream packStream = AnotherAlphaMod.texturePackList.selectedTexturePack.getResourceAsStream(path);
		if (packStream != null) {
			return packStream;
		}

		// Fallback to vanilla resource
		return clazz.getResourceAsStream(path);
	}
}
