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

import me.denyol.blockbank.api.BlockBankApi;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Created by Daniel on 28/1/17.
 */
public class TileEntityVaultPanel extends VaultTileEntityBase
{
	public static short INVENTORY_SIZE = 90;

	@CapabilityInject(IItemHandler.class)
	public static Capability<IItemHandler> ITEMS_CAPABILITY;

	private ItemStackHandler inventory = new ItemStackHandler(INVENTORY_SIZE)
	{
		@Override
		protected void onContentsChanged(int slot)
		{
			markDirty();
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
		{
			if(!BlockBankApi.hasMoneyType(stack.getItem()))
				return stack;

			return super.insertItem(slot, stack, simulate);
		}
	};

	ItemStackHandler getInventory()
	{
		return inventory;
	}

	/* Capabilities */

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if (capability == ITEMS_CAPABILITY) return true;
		return super.hasCapability(capability, facing);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == ITEMS_CAPABILITY) return (T) inventory;
		return super.getCapability(capability, facing);
	}

	/* NBT Data */

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);

		tag.setTag("inventory", ITEMS_CAPABILITY.writeNBT(inventory, null));
		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);

		if (tag.getTagId("inventory") == Constants.NBT.TAG_LIST)
		{
			ITEMS_CAPABILITY.readNBT(inventory, null, tag);
		}
	}
}
