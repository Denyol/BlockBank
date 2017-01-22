package me.denyol.blockbank.gui.slots;

import me.denyol.blockbank.tileentity.TileEntityCounterfeitDetector;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
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
