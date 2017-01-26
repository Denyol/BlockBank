package me.denyol.blockbank.blocks;

import me.denyol.blockbank.BlockBank;
import me.denyol.blockbank.network.ModGuiHandler;
import me.denyol.blockbank.tileentity.TileEntityCoinForge;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * Created by Daniel on 22/1/17.
 */
public class BlockCoinForge extends BlockBase implements ITileEntityProvider
{
	public static final PropertyDirection PROPERTY_FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool PROPERTY_ACTIVE = PropertyBool.create("active");

	public BlockCoinForge(ModBlocks.Blocks block)
	{
		super(block);

		this.setDefaultState(this.getDefaultState().withProperty(PROPERTY_FACING, EnumFacing.NORTH).withProperty(PROPERTY_ACTIVE, false));
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing facing = EnumFacing.getHorizontal(meta & 3);

		boolean active = meta >> 2 != 0; // if its not 0 active = true

		return this.getDefaultState().withProperty(PROPERTY_FACING, facing).withProperty(PROPERTY_ACTIVE, active);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		EnumFacing facing = (EnumFacing) state.getValue(PROPERTY_FACING);
		boolean active = (boolean) state.getValue(PROPERTY_ACTIVE);

		return facing.getHorizontalIndex() | ((active ? 1 : 0) << 2);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return state;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {PROPERTY_FACING, PROPERTY_ACTIVE});
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		if(placer != null)
		{
			return this.getDefaultState().withProperty(PROPERTY_FACING, placer.getHorizontalFacing().getOpposite());
		}

		return this.getDefaultState().withProperty(PROPERTY_FACING, EnumFacing.NORTH).withProperty(PROPERTY_ACTIVE, false);
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

	@Override
	public void registerForRendering()
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName(), "active=false,facing=north"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{

		boolean isActive = (boolean) stateIn.getValue(PROPERTY_ACTIVE);
		EnumFacing facing = (EnumFacing) stateIn.getValue(PROPERTY_FACING);

		double d0 = (double)pos.getX() + 0.5D;
		double d1 = (double)pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
		double d2 = (double)pos.getZ() + 0.5D;
		double d4 = rand.nextDouble() * 0.6D - 0.3D;

		if(isActive)
		{
			switch (facing)
			{
				case NORTH:
					worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D, null);
					break;
				case SOUTH:
					worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D, null);
					break;
				case WEST:
					worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D, null);
					break;
				case EAST:
					worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D, null);
					break;
			}
		}
	}
}
