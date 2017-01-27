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

import me.denyol.blockbank.tileentity.TileEntityCounterfeitDetector;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.common.UsernameCache;

import java.util.UUID;

public class SlotCounterfeitDetector extends Slot
{

	private int lastSlotSize = 0;
	private final TileEntityCounterfeitDetector tileEntity;

	public SlotCounterfeitDetector(IInventory inventoryIn, int index, int xPosition, int yPosition, TileEntityCounterfeitDetector te)
	{
		super(inventoryIn, index, xPosition, yPosition);
		tileEntity = te;
	}
	
	@Override
	public int getSlotStackLimit() // only allow one item at a time in the slot
	{
		return 1;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return tileEntity.isItemValidForSlot(0, stack);
	}

	@Override
	public void onSlotChanged()
	{
		super.onSlotChanged();

		ItemStack stack = this.inventory.getStackInSlot(getSlotIndex());
		if(stack != null && lastSlotSize < stack.stackSize)
		{
			if(!stack.hasTagCompound() || !stack.getTagCompound().hasKey("Owner"))
				return;
			else
			{
				UUID uuid = UUID.fromString(stack.getTagCompound().getString("Owner"));

				String username = UsernameCache.getLastKnownUsername(uuid);

				if(username != null)
				{
					NBTTagList lore = new NBTTagList();
					lore.appendTag(new NBTTagString("Created by: " + username));

					NBTTagCompound tag = stack.getTagCompound();

					NBTTagCompound display = new NBTTagCompound();
					display.setTag("Lore", lore);

					tag.setTag("display", display);
					stack.setTagCompound(tag);
				}
			}
		}
		else
		{
			this.lastSlotSize = 0;
		}
	}
}
