package me.denyol.blockbank.proxy;

import me.denyol.blockbank.blocks.ModBlocks;
import me.denyol.blockbank.items.ModItems;
import me.denyol.blockbank.tileentity.TileEntityCounterfeitDetector;
import me.denyol.blockbank.tileentity.render.RendererCounterfeitDetector;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy implements IBlockBankProxy
{

	@Override
	public void registerRenders()
	{
		ModItems.registerForRendering();
		ModBlocks.registerForRendering();
	}
	
	@Override
	public void registerTESRs()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCounterfeitDetector.class, new RendererCounterfeitDetector());
	}
	
}
