package me.denyol.blockbank.tileentity.vault;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

/**
 * Created by Daniel on 27/1/17.
 */
public class VaultTileEntityBase extends TileEntity
{
	boolean hasMaster = false;
	boolean isMaster = false;
	BlockPos masterPos;

	public boolean hasMaster()
	{
		return hasMaster && masterPos != null;
	}

	public boolean isMaster()
	{
		return isMaster;
	}

	public BlockPos getMasterPos()
	{
		return masterPos;
	}

	public void setHasMaster(boolean value)
	{
		hasMaster = value;
	}

	public void setIsMaster(boolean value)
	{
		isMaster = value;
	}

	public void setMasterPos(@Nonnull BlockPos pos)
	{
		masterPos = pos;
	}
}
