package me.denyol.blockbank.gui.slots;

import me.denyol.blockbank.tileentity.TileEntityCounterfitDetector;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCounterfitDetector extends Slot
{
	
	private final TileEntityCounterfitDetector tileEntity;

	public SlotCounterfitDetector(IInventory inventoryIn, int index, int xPosition, int yPosition, TileEntityCounterfitDetector te)
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

}
