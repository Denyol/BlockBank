package me.denyol.blockbank.network;

import me.denyol.blockbank.gui.ContainerCoinForge;
import me.denyol.blockbank.gui.ContainerTileEntityCounterfeitDetector;
import me.denyol.blockbank.gui.client.GuiCoinForge;
import me.denyol.blockbank.gui.client.GuiTileEntityCounterfeitDetector;
import me.denyol.blockbank.tileentity.TileEntityCoinForge;
import me.denyol.blockbank.tileentity.TileEntityCounterfeitDetector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGuiHandler implements IGuiHandler
{
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		if(ID == GUI.COUNTERFEIT_DETECTOR.ordinal() && te instanceof TileEntityCounterfeitDetector)
			return new ContainerTileEntityCounterfeitDetector(player.inventory, (TileEntityCounterfeitDetector) te);
		else if (ID == GUI.COIN_FORGE.ordinal() && te instanceof TileEntityCoinForge)
			return new ContainerCoinForge((TileEntityCoinForge) te, player.inventory);
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		if(ID == GUI.COUNTERFEIT_DETECTOR.ordinal() && te instanceof TileEntityCounterfeitDetector)
			return new GuiTileEntityCounterfeitDetector(player.inventory, (TileEntityCounterfeitDetector) te);
		else if (ID == GUI.COIN_FORGE.ordinal() && te instanceof TileEntityCoinForge)
			return new GuiCoinForge(player.inventory, (TileEntityCoinForge) te);
		
		return null;
	}
	
	public enum GUI
	{
		COUNTERFEIT_DETECTOR, COIN_FORGE
	}

}
