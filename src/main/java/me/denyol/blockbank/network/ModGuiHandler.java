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

package me.denyol.blockbank.network;

import me.denyol.blockbank.gui.ContainerCoinForge;
import me.denyol.blockbank.gui.ContainerTileEntityCounterfeitDetector;
import me.denyol.blockbank.gui.ContainerWallet;
import me.denyol.blockbank.gui.client.GuiCoinForge;
import me.denyol.blockbank.gui.client.GuiTileEntityCounterfeitDetector;
import me.denyol.blockbank.gui.client.GuiWallet;
import me.denyol.blockbank.tileentity.TileEntityCoinForge;
import me.denyol.blockbank.tileentity.TileEntityCounterfeitDetector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.items.CapabilityItemHandler;

public class ModGuiHandler implements IGuiHandler
{
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		ItemStack clicked = player.getHeldItemMainhand();
		if(ID == GUI.COUNTERFEIT_DETECTOR.ordinal() && te instanceof TileEntityCounterfeitDetector)
			return new ContainerTileEntityCounterfeitDetector(player.inventory, (TileEntityCounterfeitDetector) te);
		else if (ID == GUI.COIN_FORGE.ordinal() && te instanceof TileEntityCoinForge)
			return new ContainerCoinForge((TileEntityCoinForge) te, player.inventory);
		else if (ID == GUI.WALLET.ordinal() && clicked != null && clicked.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
			return new ContainerWallet(player.inventory, clicked);
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		ItemStack clicked = player.getHeldItemMainhand();
		if(ID == GUI.COUNTERFEIT_DETECTOR.ordinal() && te instanceof TileEntityCounterfeitDetector)
			return new GuiTileEntityCounterfeitDetector(player.inventory, (TileEntityCounterfeitDetector) te);
		else if (ID == GUI.COIN_FORGE.ordinal() && te instanceof TileEntityCoinForge)
			return new GuiCoinForge(player.inventory, (TileEntityCoinForge) te);
		else if (ID == GUI.WALLET.ordinal() && clicked != null && clicked.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
			return new GuiWallet(clicked, player.inventory);
		
		return null;
	}
	
	public enum GUI
	{
		COUNTERFEIT_DETECTOR, COIN_FORGE, WALLET
	}

}
