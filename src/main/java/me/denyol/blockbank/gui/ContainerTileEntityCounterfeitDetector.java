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

package me.denyol.blockbank.gui;

import me.denyol.blockbank.gui.slots.SlotCounterfeitDetector;
import me.denyol.blockbank.items.ModItems;
import me.denyol.blockbank.tileentity.TileEntityCounterfeitDetector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTileEntityCounterfeitDetector extends Container
{

	private TileEntityCounterfeitDetector tileEntity;

	public ContainerTileEntityCounterfeitDetector(IInventory playerInv, TileEntityCounterfeitDetector te)
	{
		this.tileEntity = te;

		addSlotToContainer(new SlotCounterfeitDetector(tileEntity, 0, 80, 20, tileEntity));
		
		addPlayerInventory(playerInv);
	}
	
	protected void addPlayerInventory(IInventory inventory)
	{	
		// for each inventory row
		for (int row = 0; row < 3; row++)
        {
			// for each column
            for (int col = 0; col < 9; col++)
            {
                this.addSlotToContainer(new Slot(inventory, col + row * 9 + 9, 8 + col * 18, row * 18 + 51));
            }
        }

        for (int row = 0; row < 9; row++) // 9 slots in the hotbar
        {
            this.addSlotToContainer(new Slot(inventory, row, 8 + row * 18, 109));
        }

	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return tileEntity.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack previous = null;
	    Slot slot = this.inventorySlots.get(index);

	    if (slot != null && slot.getHasStack())
	    {
	        ItemStack current = slot.getStack();
	        previous = current.copy();

	        if(index < 1) // if its a TE slot // From TE Inventory to Player Inventory
	        {
	        	if (!this.mergeItemStack(current, 1, 37, true)) // merge into any inventory slot
	                return null;
	        }
	        else if ((current.getItem() == ModItems.goldCoin // from inventory to TE's inventory
	        		|| current.getItem() == ModItems.emeraldCoin
	        		|| current.getItem() == ModItems.ironCoin) && current.stackSize == 1)
	        {
	        	if (!this.mergeItemStack(current, 0, 0, false))
	                return null;
	        }
	        else
	        	return null; // it failed

	        if (current.stackSize == 0)
	            slot.putStack(null);
	        else
	            slot.onSlotChanged();

	        if (current.stackSize == previous.stackSize)
	            return null;
	        
	        slot.onPickupFromSlot(playerIn, current);
	    }
	    return previous;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer playerIn)
	{
		super.onContainerClosed(playerIn);
		this.tileEntity.closeInventory(playerIn);
	}

}
