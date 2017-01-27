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
