package me.denyol.blockbank.gui.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Created by Daniel on 22/1/17.
 */
public class SlotCoinForgeOutput extends Slot
{

	public SlotCoinForgeOutput(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(@Nullable ItemStack stack)
	{
		return false;
	}
}
