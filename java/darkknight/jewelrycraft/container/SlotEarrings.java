package darkknight.jewelrycraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import darkknight.jewelrycraft.item.ItemEarrings;

public class SlotEarrings extends Slot
{
    
    /**
     * @param tile
     * @param slotID
     * @param x
     * @param y
     */
    public SlotEarrings(IInventory tile, int slotID, int x, int y)
    {
        super(tile, slotID, x, y);
    }
    
    /**
     * @param stack
     * @return
     */
    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem() instanceof ItemEarrings;
    }
    
    /**
     * @param amount
     * @return
     */
    @Override
    public ItemStack decrStackSize(int amount)
    {
        return super.decrStackSize(amount);
    }
    
    /**
     * @param player
     * @return
     */
    @Override
    public boolean canTakeStack(EntityPlayer player)
    {
        return true;
    }
}
