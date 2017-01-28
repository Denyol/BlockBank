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

import javax.annotation.Nonnull;
import java.util.UUID;

/**
 * Created by Daniel on 28/1/17.
 */
public class TileEntityVaultPanel extends VaultTileEntityBase
{
	private UUID owner;

	public void setOwner(@Nonnull UUID owner)
	{
		this.owner = owner;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		NBTTagCompound tag = super.writeToNBT(compound);

		if(owner != null)
			tag.setString("owner", owner.toString());

		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);

		if(tag.hasKey("owner"))
			this.owner = UUID.fromString(tag.getString("owner"));
	}
}
