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

import me.denyol.blockbank.gui.slots.SlotCoinForgeMaterialInput;
import me.denyol.blockbank.gui.slots.SlotCoinForgeMoldSlot;
import me.denyol.blockbank.gui.slots.SlotCoinForgeOutput;
import me.denyol.blockbank.items.ModItems;
import me.denyol.blockbank.tileentity.TileEntityCoinForge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Daniel on 22/1/17.
 */
public class ContainerCoinForge extends Container
{

	private final TileEntityCoinForge te;
	private int forgeBurnTime;
	private int currentItemBurnTime;
	private int cookTime;
	private int totalCookTime;

	// 0: input, 1: moldslot 2: fuel, 3: output
	public ContainerCoinForge(TileEntityCoinForge tileEntity, InventoryPlayer inventoryPlayer)
	{
		te = tileEntity;

		addSlotToContainer(new SlotCoinForgeMaterialInput(te, inventoryPlayer, 0, 36, 17)); // input
		addSlotToContainer(new SlotCoinForgeMoldSlot(te, 1, 62, 35));
		addSlotToContainer(new SlotFurnaceFuel(te, 2, 36, 53)); // fuel
		addSlotToContainer(new SlotCoinForgeOutput(te, 3, 120, 35)); // output

		addPlayerInventory(inventoryPlayer);
	}

	protected void addPlayerInventory(InventoryPlayer playerInv)
	{
		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k)
		{
			this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return te.isUseableByPlayer(playerIn);
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

			if(index < te.getSizeInventory()) // if its a TE slot // From TE Inventory to Player Inventory
			{
				if (!this.mergeItemStack(current, te.getSizeInventory(), 39, true)) // merge into any inventory slot
					return null;
			}
			else if (index > te.getSizeInventory()) // anything other than te's slots
			{
				if(current.getItem() == ModItems.coinMold)
				{
					if (!this.mergeItemStack(current, 1, 1, false)) ;
					return null;
				}
			}


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

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i)
		{
			IContainerListener icontainerlistener = this.listeners.get(i);

			if (this.cookTime != this.te.getField(2))
			{
				icontainerlistener.sendProgressBarUpdate(this, 2, this.te.getField(2));
			}

			if (this.forgeBurnTime != this.te.getField(0))
			{
				icontainerlistener.sendProgressBarUpdate(this, 0, this.te.getField(0));
			}

			if (this.currentItemBurnTime != this.te.getField(1))
			{
				icontainerlistener.sendProgressBarUpdate(this, 1, this.te.getField(1));
			}

			if (this.totalCookTime != this.te.getField(3))
			{
				icontainerlistener.sendProgressBarUpdate(this, 3, this.te.getField(3));
			}
		}

		this.cookTime = this.te.getField(2);
		this.forgeBurnTime = this.te.getField(0);
		this.currentItemBurnTime = this.te.getField(1);
		this.totalCookTime = this.te.getField(3);
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		this.te.setField(id, data);
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, te);
	}
}
