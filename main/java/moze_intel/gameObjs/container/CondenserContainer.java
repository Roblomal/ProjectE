package moze_intel.gameObjs.container;

import moze_intel.gameObjs.container.slots.SlotCondenserInput;
import moze_intel.gameObjs.container.slots.SlotCondenserLock;
import moze_intel.gameObjs.tiles.CondenserTile;
import moze_intel.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class CondenserContainer extends Container
{	
	private CondenserTile tile;
	
	public CondenserContainer(InventoryPlayer invPlayer, CondenserTile condenser)
	{
		tile = condenser;
		tile.openInventory();
		
		//Item Lock Slot
		this.addSlotToContainer(new SlotCondenserLock(tile, 0, 12, 6));
		
		//Condenser Inventory
		for (int i = 0; i < 7; i++) 
		      for (int j = 0; j < 13; j++)
		    	  this.addSlotToContainer(new SlotCondenserInput(tile, 1 + j + i * 13, 12 + j * 18, 26 + i * 18));
		    	  
		//Player Inventory
		for(int i = 0; i < 3; i++)
			  for(int j = 0; j < 9; j++) 
			        this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 48 + j * 18, 154 + i * 18));
		
		//Player Hotbar
		for (int i = 0; i < 9; i++)
			this.addSlotToContainer(new Slot(invPlayer, i, 48 + i * 18, 212));
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
	{
		Slot slot = this.getSlot(slotIndex);
		
		if (slot == null || !slot.getHasStack()) 
		{
			return null;
		}
		
		ItemStack stack = slot.getStack();
		ItemStack newStack = stack.copy();
		
		if (slotIndex <= 91)
		{
			if (!this.mergeItemStack(stack, 92, 127, false))
				return null;
		}
		else if (!Utils.doesItemHaveEmc(stack) || !this.mergeItemStack(stack, 1, 91, false))
		{
			return null;
		}
		
		if (stack.stackSize == 0)
		{
			slot.putStack((ItemStack) null);
		}
		
		else slot.onSlotChanged();
		slot.onPickupFromSlot(player, stack);
		return newStack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) 
	{
		return true;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		tile.closeInventory();
	}
}
