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
import me.denyol.blockbank.blocks.vault.BlockVaultPart;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
		checkMultiBlock();
		return master;
	}

	public void setMaster(VaultTileEntityBase tileMaster)
	{
		if (tileMaster == null)
		{
			hasMaster = false;
			master = null;
			isMaster = false;

			return;
		}

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

	private void checkMultiBlock()
	{
		if(!worldObj.isRemote)
		{
			if (master == null || master.isInvalid())
			{
				List<VaultTileEntityBase> connectedBlocks;
				int casingCount = 0;
				int wallCount = 0;
				int doorCount = 0;
				int panelCount = 0;

				connectedBlocks = searchBlocks();

				for(VaultTileEntityBase te : connectedBlocks)
				{
					if(te instanceof TileEntityVaultDoor)
						doorCount++;
					else if(te instanceof TileEntityVaultPanel)
						panelCount++;
					else if (te instanceof TileEntityVaultWall)
						wallCount++;
					else if (te instanceof TileEntityVaultCasing)
						casingCount++;
				}

				if(doorCount == 3 && panelCount == 1 && casingCount == 32 && wallCount == 20)
				{
					for (VaultTileEntityBase te : connectedBlocks)
					{
						te.setMaster(this);
						updateBlock(true);
						te.markDirty();
					}

					BlockBank.logger.info("New vault at: " + getPos().toString());
				}
			}
		}
	}

	public void blockRemoved()
	{
		if(isMaster())
		{
			BlockBank.logger.info("Vault at: " + getPos() + " is no longer valid.");

			List<VaultTileEntityBase> connectedBlocks;

			connectedBlocks = searchBlocks();

			for(VaultTileEntityBase te : connectedBlocks)
			{
				te.setMaster(null);
				updateBlock(false);
				te.markDirty();
			}

		}
		else if(hasMaster())
			getMaster().blockRemoved();
	}

	/**
	 * Searches blocks in a 4x4x4 radius in a positive x and y fashion, and adds all valid vault blocks found to a list.
	 * @return list of VaultTileEntityBase
	 */
	private List<VaultTileEntityBase> searchBlocks()
	{
		if(!worldObj.isRemote)
		{
			int xCoord = pos.getX();
			int yCoord = pos.getY();
			int zCoord = pos.getZ();

			List<VaultTileEntityBase> result = new ArrayList<>();

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
								result.add((VaultTileEntityBase) tileEntity);
							}
						}
					}
				}
			}

			return result;
		}

		return new ArrayList<VaultTileEntityBase>();
	}

	private void updateBlock(boolean isMultiBlock)
	{
		if(!worldObj.isRemote)
		{
			worldObj.getBlockState(getPos()).withProperty(BlockVaultPart.PROPERTY_MULTIBLOCK, isMultiBlock);
		}
	}

	@Override
	public void invalidate()
	{
		super.invalidate();
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return newState.getBlock() != oldState.getBlock();
	}

	public void notifyNeighbours()
	{
		if(!worldObj.isRemote)
		{
			checkMultiBlock();

			for(EnumFacing dir : BlockVaultPart.SIDES)
			{
				TileEntity te = worldObj.getTileEntity(pos.offset(dir));
				if(te != null && te instanceof VaultTileEntityBase)
					((VaultTileEntityBase) te).notifyNeighbours();
			}
		}
	}

	/* ITickable */

	@Override
	public void update()
	{
		if (firstRun)
		{
			checkMultiBlock();
			firstRun = false;
		}
	}

	/* NBT Saving */

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);

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
