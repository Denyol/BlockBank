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
