package moze_intel.gameObjs.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemCharge extends ItemBase
{
	byte numCharges;
	
	public ItemCharge(String unlocalName, byte numCharges)
	{
		this.numCharges = numCharges;
		this.setUnlocalizedName(unlocalName);
		this.setMaxStackSize(1);
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
    {
        return stack.hasTagCompound();
    }
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
    {
		byte charge = getCharge(stack);
		
		//Must be beetween 0.0D - 1.0D
		return charge == 0 ? 1.0D : 1.0D - (double) charge / (double) (numCharges - 1);
    }
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) 
	{
		if (!world.isRemote)
		{
			stack.stackTagCompound = new NBTTagCompound();
		}
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) 
	{
		if (!stack.hasTagCompound())
		{
			stack.stackTagCompound = new NBTTagCompound();
		}
	}
	
	public byte getCharge(ItemStack stack)
	{
		return stack.stackTagCompound.getByte("Charge");
	}
	
	private void changeCharge(ItemStack stack, boolean isSneaking)
	{
		byte currentCharge = getCharge(stack);
		if (isSneaking)
		{
			if (currentCharge > 0)
			{
				stack.stackTagCompound.setByte("Charge", (byte) (currentCharge - 1));
			}
		}
		else if (currentCharge < numCharges - 1)
		{
			stack.stackTagCompound.setByte("Charge", (byte) (currentCharge + 1));
		}
	}
	
	public void changeCharge(EntityPlayer player)
	{
		changeCharge(player.getHeldItem(), player.isSneaking());
	}
}
