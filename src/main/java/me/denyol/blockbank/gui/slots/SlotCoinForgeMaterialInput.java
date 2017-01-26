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
