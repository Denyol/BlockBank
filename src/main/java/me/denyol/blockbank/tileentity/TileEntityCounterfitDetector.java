package me.denyol.blockbank.tileentity;

import me.denyol.blockbank.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityCounterfitDetector extends TileEntityBase implements IInventory
{

	private ItemStack inspectedCoin;
	private String customName; // for renaming the inventory in an anvil

	public TileEntityCounterfitDetector()
	{
		super();
	}

	/**
	 * Sets the inspectedCoin ItemStack in the TE, setting it to null will
	 * remove the coin.
	 * 
	 * @param item
	 *            the coin ItemStack
	 */
	public void setInspectedCoin(ItemStack item)
	{
		this.inspectedCoin = item;
		
		markDirty();
	}

	// writes the data to the TE's NBT tag
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		if(this.inspectedCoin != null)
		{
			NBTTagCompound itemStack = new NBTTagCompound();
			this.inspectedCoin.writeToNBT(itemStack);
			compound.setTag("InspectedCoinStack", itemStack);
		}
		
		if(this.hasCustomName())
			compound.setString("CustomName", this.customName);

		return compound;
	}

	// saves the data from the TE's nbt tab
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		if(compound.hasKey("InspectedCoinStack"))
			this.setInventorySlotContents(0,  ItemStack.loadItemStackFromNBT(compound.getCompoundTag("InspectedCoinStack")));
		
		if(compound.hasKey("CustomName"))
			this.setCustomName(compound.getString("CustomName"));
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		return new SPacketUpdateTileEntity(this.pos, this.getBlockMetadata(), getUpdateTag());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
	{
		NBTTagCompound tag = packet.getNbtCompound();
        readFromNBT(tag);
        System.out.println("onDataPacket()");
	}
	
	@Override
	public NBTTagCompound getUpdateTag()
	{
		return writeToNBT(super.getUpdateTag());
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag)
	{
		readFromNBT(tag);
	}

	public String getCustomName()
	{
		return this.customName;
	}

	public void setCustomName(String name)
	{
		this.customName = name;
	}

	@Override
	public String getName()
	{
		return this.hasCustomName() ? this.customName : "container.tile_entity_counterfit_detector";
	}

	@Override
	public boolean hasCustomName()
	{
		return this.customName != null && !(this.customName.length() == 0);
	}
	
	@Override
	public ITextComponent getDisplayName()
    {
        return (ITextComponent)(this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]));
    }

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		if (index < 0 || index >= this.getSizeInventory())
			return null;

		return this.inspectedCoin; // we only have one slot, this is it
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if (this.getStackInSlot(index) != null)
		{
			ItemStack itemStack;

			if (this.getStackInSlot(index).stackSize <= 0) // if the stack is
			// empty
			{
				itemStack = this.getStackInSlot(index); // set itemStack to the
				// empty stack
				this.setInventorySlotContents(index, null); // set the item in
				// the inventory to
				// null
				this.markDirty(); // make sure it gets saved with the chunk
				return itemStack;
			} else
			{
				itemStack = this.getStackInSlot(index).splitStack(count);

				if (this.getStackInSlot(index).stackSize <= 0)
				{
					this.setInventorySlotContents(index, null);
				} else
				{
					this.setInventorySlotContents(index, this.getStackInSlot(index));
				}

				this.markDirty();
				return itemStack;
			}
		} else
		{
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		ItemStack stack = this.getStackInSlot(index);
		this.setInventorySlotContents(index, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if (index < 0 || index >= this.getSizeInventory())
			return;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();

		if (stack != null && stack.stackSize == 0)
			stack = null;

		this.setInspectedCoin(stack);
		this.markDirty();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return this.worldObj.getTileEntity(this.getPos()) == this
				&& player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if (stack.getItem() == ModItems.goldCoin 
				|| stack.getItem() == ModItems.ironCoin
				|| stack.getItem() == ModItems.emeraldCoin)
			return true;

		return false;
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear() // clear the inv
	{
		this.setInspectedCoin(null);
	}

}
