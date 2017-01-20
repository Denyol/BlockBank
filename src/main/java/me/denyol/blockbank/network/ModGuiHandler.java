package me.denyol.blockbank.network;

import me.denyol.blockbank.gui.ContainerTileEntityCounterfitDetector;
import me.denyol.blockbank.gui.GuiTileEntityCounterfitDetector;
import me.denyol.blockbank.tileentity.TileEntityCounterfitDetector;
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
		if(ID == GUIS.COUNTERFIT_DETECTOR.ordinal() && te instanceof TileEntityCounterfitDetector)
			return new ContainerTileEntityCounterfitDetector(player.inventory, (TileEntityCounterfitDetector) te);
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		if(ID == GUIS.COUNTERFIT_DETECTOR.ordinal() && te instanceof TileEntityCounterfitDetector)
			return new GuiTileEntityCounterfitDetector(player.inventory, (TileEntityCounterfitDetector) te);
		
		return null;
	}
	
	public enum GUIS
	{
		COUNTERFIT_DETECTOR
	}

}
