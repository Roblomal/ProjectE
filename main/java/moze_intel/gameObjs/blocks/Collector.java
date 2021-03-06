package moze_intel.gameObjs.blocks;

import moze_intel.MozeCore;
import moze_intel.gameObjs.tiles.CollectorMK1Tile;
import moze_intel.gameObjs.tiles.CollectorMK2Tile;
import moze_intel.gameObjs.tiles.CollectorMK3Tile;
import moze_intel.utils.Constants;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Collector extends BlockDirection
{
	@SideOnly(Side.CLIENT)
	private IIcon front;
	@SideOnly(Side.CLIENT)
	private IIcon top;
	private int tier;
	
	public Collector(int tier) 
	{
		super(Material.glass);
		this.setBlockName("collector_MK"+Integer.toString(tier));
		this.setLightLevel(Constants.COLLECTOR_LIGHT_VALS[tier - 1]);
		this.setHardness(0.3f);
		this.tier = tier;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
			switch (tier)
			{
				case 1:
					player.openGui(MozeCore.instance, Constants.COLLECTOR1_GUI, world, x, y, z);
					break;
				case 2:
					player.openGui(MozeCore.instance, Constants.COLLECTOR2_GUI, world, x, y, z);
					break;
				case 3:
					player.openGui(MozeCore.instance, Constants.COLLECTOR3_GUI, world, x, y, z);
					break;
			}
		return true;
	}
	
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entLiving, ItemStack itemStack)
	{
		int l = MathHelper.floor_double((double)(entLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		if (l == 0)
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);

        if (l == 1)
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);

        if (l == 2)
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);

        if (l == 3)
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		this.blockIcon = register.registerIcon("projecte:collectors/other");
		this.front = register.registerIcon("projecte:collectors/front");
		this.top = register.registerIcon("projecte:collectors/top_"+Integer.toString(tier));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
	{
		if (meta == 0 && side == 3) return front;
		if (side == 1) return top;
		return side != meta ? this.blockIcon : front;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) 
	{
		if (tier == 1) return new CollectorMK1Tile();
		if (tier == 2) return new CollectorMK2Tile();
		if (tier == 3) return new CollectorMK3Tile();
		return null;
	}
}
