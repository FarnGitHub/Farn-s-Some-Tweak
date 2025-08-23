package farn.another_alpha_mod.sub_mod.texturepack;

import java.awt.image.BufferedImage;
import java.io.IOException;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;

public class TexturePackDefault extends TexturePackBase {
	private int texturePackName = -1;
	private BufferedImage texturePackThumbnail;

	public TexturePackDefault() {
		this.texturePackFileName = "Default";
		this.firstDescriptionLine = "The default look of Minecraft";

		try {
			this.texturePackThumbnail = ImageIO.read(TexturePackDefault.class.getResource("/pack.png"));
		} catch (IOException iOException2) {
			iOException2.printStackTrace();
		}

	}

	public void func_6484_b(Minecraft minecraft1) {
		if(this.texturePackThumbnail != null) {
			minecraft1.textureManager.remove(this.texturePackName);
		}

	}

	public void bindThumbnailTexture(Minecraft minecraft1) {
		if(this.texturePackThumbnail != null && this.texturePackName < 0) {
			this.texturePackName = minecraft1.textureManager.bind(this.texturePackThumbnail);
		}

		if(this.texturePackThumbnail != null) {
			minecraft1.textureManager.bind(this.texturePackName);
		} else {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, minecraft1.textureManager.load("/gui/unknown_pack.png"));
		}

	}
}
