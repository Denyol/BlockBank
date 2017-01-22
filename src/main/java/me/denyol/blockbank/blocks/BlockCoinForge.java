package me.denyol.blockbank.blocks;

import me.denyol.blockbank.BlockBank;
import me.denyol.blockbank.network.ModGuiHandler;
import me.denyol.blockbank.tileentity.TileEntityCoinForge;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Daniel on 22/1/17.
 */
public class BlockCoinForge extends BlockBase implements ITileEntityProvider
{

	public static final PropertyDirection PROPERTY_FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockCoinForge(ModBlocks.Blocks block)
	{
		super(block);

		this.setDefaultState(this.blockState.getBaseState().withProperty(PROPERTY_FACING, EnumFacing.NORTH));
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing facing = EnumFacing.getHorizontal(meta);

		return this.getDefaultState().withProperty(PROPERTY_FACING, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		EnumFacing facing = (EnumFacing) state.getValue(PROPERTY_FACING);


		return facing.getHorizontalIndex();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_FACING);
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		if(placer != null)
		{
			return this.getDefaultState().withProperty(PROPERTY_FACING, placer.getHorizontalFacing().getOpposite());
		}

		return this.getDefaultState().withProperty(PROPERTY_FACING, EnumFacing.NORTH);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityCoinForge();
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntityCoinForge te = (TileEntityCoinForge) worldIn.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(worldIn, pos, te);
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if(stack.hasDisplayName())
			((TileEntityCoinForge) worldIn.getTileEntity(pos)).setCustomName(stack.getDisplayName());
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(!worldIn.isRemote)
			playerIn.openGui(BlockBank.instance, ModGuiHandler.GUI.COIN_FORGE.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());

		return true;
	}
}
