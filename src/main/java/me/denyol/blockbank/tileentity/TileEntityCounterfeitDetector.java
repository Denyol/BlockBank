package me.denyol.blockbank.tileentity;

import me.denyol.blockbank.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityCounterfeitDetector extends TileEntity implements IInventory
{

	private ItemStack inspectedCoin;
	private String customName; // for renaming the inventory in an anvil

	public TileEntityCounterfeitDetector()
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
		return this.hasCustomName() ? this.customName : "container.tile_entity_counterfeit_detector";
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
		return this.inspectedCoin; // we only have one slot, this is it
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack itemStack = ItemStackHelper.getAndSplit(new ItemStack[]{this.inspectedCoin}, index, count);

		if (itemStack != null)
		{
			this.markDirty();
		}

		return itemStack;
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
		this.inspectedCoin = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();

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
		return stack.getItem() == ModItems.goldCoin
				|| stack.getItem() == ModItems.ironCoin
				|| stack.getItem() == ModItems.emeraldCoin;

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
