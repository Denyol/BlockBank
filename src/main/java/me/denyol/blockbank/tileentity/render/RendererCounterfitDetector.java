package me.denyol.blockbank.tileentity.render;

import me.denyol.blockbank.tileentity.TileEntityCounterfitDetector;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RendererCounterfitDetector extends TileEntitySpecialRenderer<TileEntityCounterfitDetector>
{
	
	public RendererCounterfitDetector()
	{
	}
	
	@Override
	public void renderTileEntityAt(TileEntityCounterfitDetector te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		//super.renderTileEntityAt(te, x, y, z, partialTicks, -1);
		
		//EntityItem item;
		
		/*
		if(ModItems.Items.valueOf(te.inspectedCoinType) == ModItems.Items.GOLD_COIN) // crashes
			item = COIN_GOLD;
		else if(ModItems.Items.valueOf(te.inspectedCoinType) == ModItems.Items.IRON_COIN)
			item = COIN_IRON;
		else
			item = COIN_EMERALD;
			*/
		
		GlStateManager.pushMatrix();
		{
			
			GlStateManager.translate(x, y, z);
			GlStateManager.rotate(90f, 1, 0, 0);
			GlStateManager.translate(0.5, 0.05, -0.5);
			//Minecraft.getMinecraft().getRenderManager().doRenderEntity(item, 0, 0, 0, 0F, 0, false);
		
		}
		GlStateManager.popMatrix();
	}
}
