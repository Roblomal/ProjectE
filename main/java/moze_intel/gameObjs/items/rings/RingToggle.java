package moze_intel.gameObjs.items.rings;

import moze_intel.gameObjs.items.IItemModeChanger;
import moze_intel.gameObjs.items.ItemBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class RingToggle extends ItemBase implements IItemModeChanger
{
	private String name;
	@SideOnly(Side.CLIENT)
	private IIcon ringOn;
	@SideOnly(Side.CLIENT)
	private IIcon ringOff;
	
	public RingToggle(String unlocalName)
	{
		name = unlocalName;
		this.setUnlocalizedName(unlocalName);
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) 
	{
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
    {
        return false;
    }
	
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int dmg)
    {
		return dmg == 0 ? ringOff : ringOn;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
	{
		ringOn = register.registerIcon(this.getTexture("rings", name+"_on"));
		ringOff = register.registerIcon(this.getTexture("rings", name+"_off"));
	}
	
	public void changeMode(EntityPlayer player, ItemStack stack)
	{
		if (stack.getItemDamage() == 0)
		{
			stack.setItemDamage(1);
		}
		else
		{
			stack.setItemDamage(0);
		}
	}
}
