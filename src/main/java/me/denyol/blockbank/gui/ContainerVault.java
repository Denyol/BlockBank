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

import me.denyol.blockbank.tileentity.vault.TileEntityVaultPanel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.List;

/**
 * Created by Daniel on 6/2/17.
 */
public class ContainerVault extends Container //TODO finish this
{

	private final TileEntityVaultPanel tileEntity;
	private final IItemHandler inv;
	private int rows = 0;

	public ContainerVault(IInventory playerInv, TileEntityVaultPanel te)
	{
		tileEntity = te;

		inv = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		if(inv != null && inv instanceof ItemStackHandler)
		{
			rows = ((ItemStackHandler) inv).getSlots() / 9;
			for (int row = 0; row < rows; row++)
			{
				for (int col = 0; col < 9; col++)
				{
					addSlotToContainer(new SlotItemHandler(inv, col + row * 9, 8 + col * 18, 18 + row * 18));
				}
			}
		}

		//scrollTo(0.0F);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return player.getDistanceSq(tileEntity.getPos().add(0.5, 0.5, 0.5)) <= 64;
	}


	/**
	 * Updates the gui slots ItemStack's based on scroll position.
	 */
	public void scrollTo(int row)
	{

		for (Slot slot : (List<Slot>) inventorySlots)
		{
			if (!(slot.inventory instanceof InventoryPlayer)) {
				slot.xDisplayPosition = -99999; // TODO FINISH https://github.com/Questology/Questology/blob/master/src/main/java/demonmodders/questology/container/ContainerSummoner.java
			}
		}

		/*
		int i = (this.itemList.size() + 9 - 1) / 9 - 6;
		int j = (int)((double)(position * (float)i) + 0.5D);

		if (j < 0)
		{
			j = 0;
		}

		for (int row = 0; row < 6; ++row) // 6 for number of visible rows
		{
			for (int col = 0; col < 9; ++col)
			{
				int i1 = col + (row + j) * 9;

				if (i1 >= 0 && i1 < this.itemList.size())
				{
					GuiContainerCreative.basicInventory.setInventorySlotContents(col + row * 9, (ItemStack)this.itemList.get(i1));
				}
				else
				{
					GuiContainerCreative.basicInventory.setInventorySlotContents(col + row * 9, null);
				}
			}
		}*/
	}
}
