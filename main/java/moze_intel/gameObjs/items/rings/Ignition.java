package moze_intel.gameObjs.items.rings;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Ignition extends RingToggle
{
	public Ignition()
	{
		super("ignition");
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int inventorySlot, boolean par5) 
	{
		if (world.isRemote || inventorySlot > 8 || !(entity instanceof EntityPlayer)) return;
		
		super.onUpdate(stack, world, entity, inventorySlot, par5);
		EntityPlayer player = (EntityPlayer) entity;

		if (stack.getItemDamage() != 0)
		{
			if (this.getEmc(stack) == 0 && !this.consumeFuel(player, stack, 64, false))
			{
				stack.setItemDamage(0);
			}
			else 
			{
				setNearbyOnFlames(world, player);
				this.removeEmc(stack, 0.32F);
			}
		}
		else 
		{
			extinguishNearby(world, player);
		}
	}
	
	private void extinguishNearby(World world, EntityPlayer player)
	{
		for (int x = (int) (player.posX - 1); x <= player.posX + 1; x++)
			for (int y = (int) (player.posY - 1); y <= player.posY + 1; y++)
				for (int z = (int) (player.posZ - 1); z <= player.posZ + 1; z++)
					if (world.getBlock(x, y, z) == Blocks.fire)
						world.setBlockToAir(x, y, z);
	}
	
	private void setNearbyOnFlames(World world, EntityPlayer player)
	{
		for (int x = (int) (player.posX - 8); x <= player.posX + 8; x++)
			for (int y = (int) (player.posY - 5); y <= player.posY + 5; y++)
				for (int z = (int) (player.posZ - 8); z <= player.posZ + 8; z++)
					if (world.rand.nextInt(128) == 0 && world.getBlock(x, y, z) == Blocks.air)
						world.setBlock(x, y, z, Blocks.fire);
	}
	
	@Override
	public void changeMode(EntityPlayer player, ItemStack stack)
	{
		
		if (stack.getItemDamage() == 0)
		{
			if (this.getEmc(stack) == 0 && !this.consumeFuel(player, stack, 64, false))
			{
				//NOOP (used to be sounds)
			}
			else
			{
				stack.setItemDamage(1);
			}
		}
		else
		{
			stack.setItemDamage(0);
		}
	}
}
