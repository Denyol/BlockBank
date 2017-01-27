/*
 * <BlockBank Minecraft Forge economy mod>
 *     Copyright (C) <2017>  <Daniel Tucker>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.denyol.blockbank.gui.client;

import me.denyol.blockbank.BlockBank;
import me.denyol.blockbank.gui.ContainerCoinForge;
import me.denyol.blockbank.tileentity.TileEntityCoinForge;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Daniel on 22/1/17.
 */
public class GuiCoinForge extends GuiContainer
{

	private TileEntityCoinForge te;
	private InventoryPlayer player;

	public GuiCoinForge(InventoryPlayer playerInv, TileEntityCoinForge tileEntity)
	{
		super(new ContainerCoinForge(tileEntity, playerInv));

		player = playerInv;
		te = tileEntity;

		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRendererObj.drawString(this.te.getDisplayName().getUnformattedText(), 8, 6, 4210752);
		this.fontRendererObj.drawString(this.player.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.renderEngine.bindTexture(new ResourceLocation(BlockBank.MOD_ID, "textures/gui/CoinForgeContainer.png"));
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		if (te.getField(0) > 0)
		{
			int scale = this.getBurnLeftScaled(13);
			this.drawTexturedModalRect(x + 37, y + 37 + 12 - scale, 176, 12 - scale, 14, scale + 1);
		}

		int meltScale = this.getMeltProgressScaled(24);
		this.drawTexturedModalRect(x + 84, y + 34, 176, 14, meltScale + 1, 16);
	}

	private int getBurnLeftScaled(int pixels)
	{
		int currentItemBurnTime = this.te.getField(1);
		int forgeBurnTime = this.te.getField(0);

		return forgeBurnTime != 0 && currentItemBurnTime != 0 ? forgeBurnTime * pixels / currentItemBurnTime : 0;
	}

	private int getMeltProgressScaled(int pixels)
	{
		int i = this.te.getField(2);
		int j = this.te.getField(3);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}
}
