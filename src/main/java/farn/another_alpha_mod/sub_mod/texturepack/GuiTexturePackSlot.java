package farn.another_alpha_mod.sub_mod.texturepack;

import java.util.List;

import com.mojang.blaze3d.vertex.BufferBuilder;
import farn.another_alpha_mod.AnotherAlphaMod;

import org.lwjgl.opengl.GL11;

class GuiTexturePackSlot extends GuiSlot {
	final GuiTexturePacks parentTexturePackGui;

	public GuiTexturePackSlot(GuiTexturePacks guiTexturePacks1) {
		super(guiTexturePacks1.getMinecraft(), guiTexturePacks1.width, guiTexturePacks1.height, 32, guiTexturePacks1.height - 55 + 4, 36);
		this.parentTexturePackGui = guiTexturePacks1;
	}

	protected int getSize() {
		List list1 = AnotherAlphaMod.texturePackList.availableTexturePacks();
		return list1.size();
	}

	protected void elementClicked(int i1, boolean z2) {
		List list3 = AnotherAlphaMod.texturePackList.availableTexturePacks();
		AnotherAlphaMod.texturePackList.setTexturePack((TexturePackBase)list3.get(i1));
		this.parentTexturePackGui.getMinecraft().textureManager.reload();
	}

	protected boolean isSelected(int i1) {
		List list2 = AnotherAlphaMod.texturePackList.availableTexturePacks();
		return AnotherAlphaMod.texturePackList.selectedTexturePack == list2.get(i1);
	}

	protected int getContentHeight() {
		return this.getSize() * 36;
	}

	protected void drawBackground() {
		this.parentTexturePackGui.renderBackground();
	}

	protected void drawSlot(int i1, int i2, int i3, int i4, BufferBuilder tessellator5) {
		TexturePackBase texturePackBase6 = (TexturePackBase)AnotherAlphaMod.texturePackList.availableTexturePacks().get(i1);
		texturePackBase6.bindThumbnailTexture(this.parentTexturePackGui.getMinecraft());
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		tessellator5.start();
		tessellator5.color(0xFFFFFF);
		tessellator5.vertex((double)i2, (double)(i3 + i4), 0.0D, 0.0D, 1.0D);
		tessellator5.vertex((double)(i2 + 32), (double)(i3 + i4), 0.0D, 1.0D, 1.0D);
		tessellator5.vertex((double)(i2 + 32), (double)i3, 0.0D, 1.0D, 0.0D);
		tessellator5.vertex((double)i2, (double)i3, 0.0D, 0.0D, 0.0D);
		tessellator5.end();
		this.parentTexturePackGui.drawString(this.parentTexturePackGui.getMinecraft().textRenderer, texturePackBase6.texturePackFileName, i2 + 32 + 2, i3 + 1, 0xFFFFFF);
		this.parentTexturePackGui.drawString(this.parentTexturePackGui.getMinecraft().textRenderer, texturePackBase6.firstDescriptionLine, i2 + 32 + 2, i3 + 12, 8421504);
		this.parentTexturePackGui.drawString(this.parentTexturePackGui.getMinecraft().textRenderer, texturePackBase6.secondDescriptionLine, i2 + 32 + 2, i3 + 12 + 10, 8421504);
	}
}
