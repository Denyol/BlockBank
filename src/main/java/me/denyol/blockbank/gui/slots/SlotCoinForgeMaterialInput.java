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

package me.denyol.blockbank.gui.slots;

import me.denyol.blockbank.tileentity.TileEntityCoinForge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;

/**
 * Created by Daniel on 22/1/17.
 */
public class SlotCoinForgeMaterialInput extends Slot
{

	private int lastSlotSize = 0;
	InventoryPlayer playerInv;
	TileEntityCoinForge te;

	public SlotCoinForgeMaterialInput(TileEntityCoinForge tileEntity, InventoryPlayer inventoryPlayer, int index, int xPosition, int yPosition)
	{
		super(tileEntity, index, xPosition, yPosition);

		this.playerInv = inventoryPlayer;
		this.te = tileEntity;
	}

	@Override
	public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack)
	{
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("Owner"))
			stack.getTagCompound().removeTag("Owner");

		if (stack.getTagCompound().getKeySet().size() == 0)
			stack.setTagCompound(null);

		super.onPickupFromSlot(playerIn, stack);
	}

	@Override
	public void onSlotChanged()
	{
		super.onSlotChanged();

		ItemStack stack = this.inventory.getStackInSlot(getSlotIndex());
		if(stack != null && lastSlotSize < stack.stackSize)
		{
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());

			stack.getTagCompound().setString("Owner", playerInv.player.getUniqueID().toString());
		}
		else
		{
			this.lastSlotSize = 0;
		}
	}

	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}

	@Override
	public boolean isItemValid(@Nullable ItemStack stack) {
		return te.isItemValidForSlot(0, stack);
	}
}
