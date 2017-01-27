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
