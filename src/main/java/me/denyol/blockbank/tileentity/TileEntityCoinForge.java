package me.denyol.blockbank.tileentity;

import me.denyol.blockbank.api.recipe.RecipeCoinForge;
import me.denyol.blockbank.blocks.BlockCoinForge;
import me.denyol.blockbank.items.ModItems;
import me.denyol.blockbank.items.crafting.ModCraftingRecipes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Daniel on 22/1/17.
 */
public class TileEntityCoinForge extends TileEntity implements ITickable, IInventory
{

	private static final int[] SLOTS_TOP = new int[] {0};
	private static final int[] SLOTS_BOTTOM = new int[] {3, 2};
	private static final int[] SLOTS_SIDES = new int[] {1, 2};

	private String customName;
	/** 0: input, 1: moldslot 2: fuel, 3: output */
	private ItemStack[] coinForgeStacks = new ItemStack[4];
	/** The number of ticks that the forge will keep burning */
	private int forgeBurnTime; // 0
	/** The number of ticks that a fresh copy of the currently-burning fuel item would keep the forge burning for */
	private int currentItemBurnTime; // 1
	private int cookTime; // 2
	private int totalCookTime; // 3

	@Override
	public void update()
	{
		boolean hasBeenForging = forgingSomething(); //used for changing models
		boolean changedForgingState = false;

		if(forgingSomething())
			forgeBurnTime--;

		if(!worldObj.isRemote) // 0: input, 1: moldslot 2: fuel, 3: output
		{
			if(coinForgeStacks[0] != null)
			{
				// start forging
				if(!forgingSomething() && canForge())
				{
					this.forgeBurnTime = TileEntityFurnace.getItemBurnTime(coinForgeStacks[2]);
					this.currentItemBurnTime = this.forgeBurnTime;

					if (forgingSomething())
					{
						changedForgingState = true;

						if (coinForgeStacks[2] != null)
						{
							coinForgeStacks[2].stackSize--;

							if (this.coinForgeStacks[2].stackSize == 0)
							{
								this.coinForgeStacks[2] = coinForgeStacks[2].getItem().getContainerItem(coinForgeStacks[2]);
							}
						}

					}

				}
				// continue forging coin
				if(forgingSomething() && canForge())
				{
					this.cookTime++;

					if(this.cookTime == this.totalCookTime) // forging done
					{
						this.cookTime = 0;
						this.totalCookTime = timeToForgeItem(this.coinForgeStacks[0]);
						this.forgeItem();
						changedForgingState = true;
					}
				}
				else
					this.cookTime = 0;

			}

			if(hasBeenForging != forgingSomething())
			{
				if(forgingSomething())
				{
					IBlockState state = worldObj.getBlockState(this.pos);
					worldObj.setBlockState(this.pos, state.withProperty(BlockCoinForge.PROPERTY_ACTIVE, true));
				}
				else
				{
					IBlockState state = worldObj.getBlockState(this.pos);
					worldObj.setBlockState(this.pos, state.withProperty(BlockCoinForge.PROPERTY_ACTIVE, false));
				}

				changedForgingState = true;
			}
		}

		if (changedForgingState)
			markDirty();
	}

	public int timeToForgeItem(ItemStack itemStack)
	{
		return 300;
	}

	public boolean forgingSomething()
	{
		return this.forgeBurnTime > 0;
	}

	private boolean canForge()
	{
		// if nothing in inputs
		if(coinForgeStacks[0] == null || coinForgeStacks[1] == null || coinForgeStacks[2] == null)
			return false;
		else {
			// check if the input is valid and the output is empty
			return isValidCoinMaterial(coinForgeStacks[0]) && coinForgeStacks[3] == null && RecipeCoinForge.isValidMoldItem(coinForgeStacks[1].getItem());
		}
	}

	/**
	 * Turn one item from the input source stack into the appropriate forged item in the result stack
	 */
	private void forgeItem()
	{
		if (this.canForge())
		{
			ItemStack itemStack = ModCraftingRecipes.goldCoin.getOutput().copy();

			NBTTagCompound orig = this.coinForgeStacks[0].getTagCompound();

			if(orig != null && orig.hasKey("Owner"))
			{
				String uuid = orig.getString("Owner");

				if(uuid.length() > 0)
				{
					itemStack.setTagCompound(new NBTTagCompound());
					itemStack.getTagCompound().setString("Owner", uuid);
				}
			}

			if(this.coinForgeStacks[3] == null) // output is empty
			{
				this.coinForgeStacks[3] = itemStack.copy();
			}
			else if (ItemStack.areItemStackTagsEqual(this.coinForgeStacks[3] ,itemStack))
			{
				this.coinForgeStacks[3].stackSize += itemStack.stackSize;
			}

			--this.coinForgeStacks[0].stackSize;

			if(this.coinForgeStacks[0].stackSize <= 0)
				this.coinForgeStacks[0] = null;
		}
	}

	public void setCustomName(String name)
	{
		this.customName = name;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		compound.setInteger("ForgeBurnTime", this.forgeBurnTime);
		compound.setShort("CookTime", (short) this.cookTime);
		compound.setInteger("TotalCookTime", this.totalCookTime);
		compound.setInteger("CurrentItemBurnTime", this.currentItemBurnTime);

		NBTTagList list = new NBTTagList();
		for(int i = 0; i < this.getSizeInventory(); i++)
		{
			if(this.coinForgeStacks[i] != null)
			{
				NBTTagCompound stack = new NBTTagCompound();
				stack.setByte("Slot", (byte) i);
				this.coinForgeStacks[i].writeToNBT(stack);
				list.appendTag(stack);
			}
		}
		compound.setTag("Items", list);

		if(this.hasCustomName())
			compound.setString("CustomName", this.customName);

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		NBTTagList list = compound.getTagList("Items", 10);
		this.coinForgeStacks = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < list.tagCount(); ++i)
		{
			NBTTagCompound stack = list.getCompoundTagAt(i);
			int slot = stack.getByte("Slot");

			if(slot >= 0 && slot < this.coinForgeStacks.length)
				this.coinForgeStacks[slot] = ItemStack.loadItemStackFromNBT(stack);
		}

		this.forgeBurnTime = compound.getInteger("ForgeBurnTime");
		this.cookTime = (int) compound.getShort("CookTime");
		this.totalCookTime = compound.getInteger("TotalCookTime");
		this.currentItemBurnTime = compound.getInteger("CurrentItemBurnTime");

		if(compound.hasKey("CustomName"))
			this.customName = compound.getString("CustomName");
	}

	// Inventory stuff

	@Override
	public int getSizeInventory()
	{
		return this.coinForgeStacks.length;
	}

	@Nullable
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.coinForgeStacks[index];
	}

	@Nullable
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		this.markDirty();
		return ItemStackHelper.getAndSplit(this.coinForgeStacks, index, count);
	}

	@Nullable
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
		boolean sameItemInSlot = stack != null && stack.isItemEqual(this.coinForgeStacks[index]) && ItemStack.areItemStackTagsEqual(stack, this.coinForgeStacks[index]);
		this.coinForgeStacks[index] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();

		if(index == 0 && !sameItemInSlot)
		{
				this.totalCookTime = this.timeToForgeItem(stack);
				this.cookTime = 0;
		}
		markDirty();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
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
		if(index == 3)
			return false;
		else if(index == 1 && stack.getItem() == ModItems.coinMold)
			return true;
		else if (index == 2 && TileEntityFurnace.isItemFuel(stack))
			return true;
		else return index == 0 && this.isValidCoinMaterial(stack);
	}

	public int getField(int id)
	{
		switch (id)
		{
			case 0:
				return this.forgeBurnTime;
			case 1:
				return this.currentItemBurnTime;
			case 2:
				return this.cookTime;
			case 3:
				return this.totalCookTime;
			default:
				return 0;
		}
	}

	public void setField(int id, int value)
	{
		switch (id)
		{
			case 0:
				this.forgeBurnTime = value;
				break;
			case 1:
				this.currentItemBurnTime = value;
				break;
			case 2:
				this.cookTime = value;
				break;
			case 3:
				this.totalCookTime = value;
		}
	}

	public int getFieldCount()
	{
		return 4;
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < this.coinForgeStacks.length; ++i)
		{
			this.coinForgeStacks[i] = null;
		}
	}

	@Override
	public String getName()
	{
		return this.hasCustomName() ? this.customName : "container.tile_entity_coin_forge";
	}

	@Override
	public boolean hasCustomName()
	{
		return this.customName != null && this.customName.length() != 0;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return (ITextComponent) (this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]));
	}

	public static boolean isValidCoinMaterial(ItemStack stack)
	{
		return stack != null && RecipeCoinForge.isValidInputMaterial(stack.getItem());
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return newState.getBlock() != oldState.getBlock();
	}
}
