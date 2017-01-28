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

import me.denyol.blockbank.BlockBank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Daniel on 27/1/17.
 */
public abstract class VaultTileEntityBase extends TileEntity implements ITickable
{
	private UUID owner;
	private boolean hasMaster = false;
	private boolean isMaster = false;
	private boolean isFormed = false;
	private VaultTileEntityBase master;
	boolean firstRun = true;

	public boolean hasMaster()
	{
		return hasMaster && master != null;
	}

	public boolean isMaster()
	{
		return isMaster;
	}

	public VaultTileEntityBase getMaster()
	{
		initializeMultiblockIfNecessary();
		return master;
	}

	public void setMaster(@Nonnull VaultTileEntityBase tileMaster)
	{
		master = tileMaster;
		hasMaster = true;

		isMaster = master == this;
	}

	public void setOwner(@Nonnull UUID owner)
	{
		this.owner = owner;
	}

	@Nullable
	public UUID getOwner()
	{
		if(owner == null || getMaster() == null)
			return null;

		return isMaster() ? owner : getMaster().owner;
	}

	private void initializeMultiblockIfNecessary()
	{
		if(!worldObj.isRemote)
		{
			BlockBank.logger.info("Attempting New Multiblock at: " + getPos().toString());
			int xCoord = pos.getX();
			int yCoord = pos.getY();
			int zCoord = pos.getZ();

			if (master == null || master.isInvalid())
			{
				List<VaultTileEntityBase> connectedBlock = new ArrayList<>();
				int casingCount = 0;
				int wallCount = 0;
				int doorCount = 0;
				int panelCount = 0;
				// 4x4x4
				for (int x = 0; x < 5; x++) {
					for (int y = 0; y < 5; y++) {
						for (int z = 0; z < 5; z++)
						{
							BlockPos position = new BlockPos(xCoord + x, yCoord + y, zCoord + z);
							if(worldObj.isBlockLoaded(position))
							{
								TileEntity tileEntity = worldObj.getTileEntity(position);

								if (tileEntity instanceof VaultTileEntityBase)
								{
									connectedBlock.add((VaultTileEntityBase) tileEntity);

									if(tileEntity instanceof TileEntityVaultPanel)
										panelCount++;
									else if (tileEntity instanceof TileEntityVaultCasing)
										casingCount++;
									else if (tileEntity instanceof TileEntityVaultWall)
										wallCount++;
									else if (tileEntity instanceof TileEntityVaultDoor)
										doorCount++;
								}
							}
						}
					}
				}

				if(doorCount == 3 && panelCount == 1 && casingCount == 32 && wallCount == 20)
				{
					for (VaultTileEntityBase te : connectedBlock)
					{
						te.setMaster(this);
					}

					BlockBank.logger.info("New Multiblock at: " + getPos().toString());
				}

				BlockBank.logger.info("Tried to make multiblock at: " + getPos().toString() + " Amounts: " + doorCount);
			}
		}
	}

	@Override
	public void invalidate()
	{
		super.invalidate();
	}

	/* ITickable */

	@Override
	public void update()
	{
		if (firstRun)
		{
			initializeMultiblockIfNecessary();
			firstRun = false;
		}
	}

	/* NBT Saving */

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setBoolean("hasMaster", hasMaster);
		tag.setBoolean("isMaster", isMaster);

		if(owner != null)
			tag.setString("owner", owner.toString());

		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);

		hasMaster = tag.getBoolean("hasMaster");
		isMaster = tag.getBoolean("isMaster");

		if(tag.hasKey("owner"))
			this.owner = UUID.fromString(tag.getString("owner"));
	}
}
