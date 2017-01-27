package me.denyol.blockbank.tileentity.vault;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;


/**
 * Created by Daniel on 27/1/17.
 */
public class TileEntityVaultCasing extends VaultTileEntityBase
{

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setBoolean("hasMaster", hasMaster);
		tag.setBoolean("isMaster", isMaster);
		if(masterPos != null)
		{
			tag.setInteger("masterX", masterPos.getX());
			tag.setInteger("masterY", masterPos.getY());
			tag.setInteger("masterZ", masterPos.getZ());
		}

		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		if(tag.hasKey("masterX"))
		{
			int x = tag.getInteger("masterX");
			int y = tag.getInteger("masterY");
			int z = tag.getInteger("masterZ");
			masterPos = new BlockPos(x, y, z);
		}
		hasMaster = tag.getBoolean("hasMaster");
		isMaster = tag.getBoolean("isMaster");
	}
}
