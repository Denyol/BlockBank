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

package me.denyol.blockbank.tileentity.render;

import me.denyol.blockbank.tileentity.TileEntityCounterfeitDetector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

public class RendererCounterfeitDetector extends TileEntitySpecialRenderer<TileEntityCounterfeitDetector>
{

	public RendererCounterfeitDetector()
	{
	}

	@Override
	public void renderTileEntityAt(TileEntityCounterfeitDetector te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		ItemStack coin = te.getStackInSlot(0);

		if(coin != null)
		{
			GlStateManager.pushMatrix();
			{

				GlStateManager.translate(x, y, z);
				GlStateManager.rotate(90f, 1, 0, 0);
				GlStateManager.translate(0.55, 0.36, -0.51);
				GlStateManager.rotate(10f, 0, 0, 1);
				
				Minecraft.getMinecraft().getRenderItem().renderItem(coin, TransformType.GROUND);

			}
			GlStateManager.popMatrix();
		}
	}
}
