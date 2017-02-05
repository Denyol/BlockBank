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

import me.denyol.blockbank.items.ItemWalletCapabilityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Daniel on 5/2/17.
 */
public class ContainerWallet extends Container
{

	private final InventoryPlayer playerInv;

	public ContainerWallet(InventoryPlayer playerInv, @Nonnull ItemStack clicked)
	{
		IItemHandler handler = clicked.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		this.playerInv = playerInv;

		for (int row = 0; row < ItemWalletCapabilityProvider.ROWS; row++)
		{
			for (int col = 0; col < 9; col++)
			{
				addSlotToContainer(new SlotItemHandler(handler, col + row * 9, 8 + col * 18, 18 + row * 18));
			}
		}

		addPlayerInventory(playerInv);
	}

	protected void addPlayerInventory(InventoryPlayer playerInv)
	{
		for (int row = 0; row < 3; ++row)
		{
			for (int col = 0; col < 9; ++col)
			{
				this.addSlotToContainer(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 122 + row * 18));
			}
		}

		for (int k = 0; k < 9; ++k)
		{
			this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 180));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}

	@Nullable
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			int minPlayerSlot = inventorySlots.size() - playerInv.mainInventory.length;
			if (index < minPlayerSlot)
			{
				if (!this.mergeItemStack(itemstack1, minPlayerSlot, this.inventorySlots.size(), true))
					return null;
			} else if (!this.mergeItemStack(itemstack1, 0, minPlayerSlot, false))
				return null;

			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}
}
