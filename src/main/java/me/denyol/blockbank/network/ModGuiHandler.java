package me.denyol.blockbank.network;

import me.denyol.blockbank.gui.ContainerTileEntityCounterfeitDetector;
import me.denyol.blockbank.gui.GuiTileEntityCounterfeitDetector;
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
		if(ID == GUIS.COUNTERFEIT_DETECTOR.ordinal() && te instanceof TileEntityCounterfeitDetector)
			return new ContainerTileEntityCounterfeitDetector(player.inventory, (TileEntityCounterfeitDetector) te);
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		if(ID == GUIS.COUNTERFEIT_DETECTOR.ordinal() && te instanceof TileEntityCounterfeitDetector)
			return new GuiTileEntityCounterfeitDetector(player.inventory, (TileEntityCounterfeitDetector) te);
		
		return null;
	}
	
	public enum GUIS
	{
		COUNTERFEIT_DETECTOR
	}

}
