package darkknight.jewelrycraft.curses;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.BlockPressurePlateWeighted;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.entities.EntityHalfHeart;
import darkknight.jewelrycraft.entities.EntityHeart;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.tileentity.TileEntityMidasTouch;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

public class CurseMidasTouch extends Curse
{
    public CurseMidasTouch(int id, String name, int text)
    {
        super(id, name, text);
    }
    
    @Override
    public void attackedByPlayerAction(World world, EntityPlayer player, Entity target)
    {
        if (!world.isRemote && target instanceof EntityLiving && !(target instanceof EntityHeart) && !(target instanceof EntityHalfHeart) && player.inventory.getCurrentItem() == null){
            world.setBlock(MathHelper.floor_double(target.posX), MathHelper.floor_double(target.posY), MathHelper.floor_double(target.posZ), BlockList.midasTouchBlock, 0, 2);
            TileEntityMidasTouch midasTouchVictim = new TileEntityMidasTouch();
            midasTouchVictim.setEntity(target);
            world.setTileEntity(MathHelper.floor_double(target.posX), MathHelper.floor_double(target.posY), MathHelper.floor_double(target.posZ), midasTouchVictim);
            target.setDead();
        }
    }

    @Override
    public void action(World world, EntityPlayer player)
    {
        ItemStack curItem = player.inventory.getCurrentItem();
        if(curItem != null && curItem.getItem() != ItemList.goldObj && !isGoldenObject(curItem.getItem())){
            int index = player.inventory.currentItem;
            ItemStack item = curItem;
            ItemStack result = new ItemStack(ItemList.goldObj, item.stackSize, item.getItemDamage());
            JewelryNBT.addItem(result, item);
            if(changeItem(curItem) != null) result = changeItem(curItem); 
            player.inventory.setInventorySlotContents(index, result);
        }
    }
    
    public ItemStack changeItem(ItemStack currItem)
    {
        if(currItem.getItem() instanceof ItemSword) return new ItemStack(Items.golden_sword, currItem.stackSize, currItem.getItemDamage());
        else if(currItem.getItem() instanceof ItemSpade) return new ItemStack(Items.golden_shovel, currItem.stackSize, currItem.getItemDamage());
        else if(currItem.getItem() instanceof ItemPickaxe) return new ItemStack(Items.golden_pickaxe, currItem.stackSize, currItem.getItemDamage());
        else if(currItem.getItem() instanceof ItemAxe) return new ItemStack(Items.golden_axe, currItem.stackSize, currItem.getItemDamage());
        else if(currItem.getItem() instanceof ItemHoe) return new ItemStack(Items.golden_hoe, currItem.stackSize, currItem.getItemDamage());
        else if(currItem.getItem() instanceof ItemArmor && ((ItemArmor)currItem.getItem()).armorType == 0) return new ItemStack(Items.golden_helmet, currItem.stackSize, currItem.getItemDamage());
        else if(currItem.getItem() instanceof ItemArmor && ((ItemArmor)currItem.getItem()).armorType == 1) return new ItemStack(Items.golden_chestplate, currItem.stackSize, currItem.getItemDamage());
        else if(currItem.getItem() instanceof ItemArmor && ((ItemArmor)currItem.getItem()).armorType == 2) return new ItemStack(Items.golden_leggings, currItem.stackSize, currItem.getItemDamage());
        else if(currItem.getItem() instanceof ItemArmor && ((ItemArmor)currItem.getItem()).armorType == 3) return new ItemStack(Items.golden_boots, currItem.stackSize, currItem.getItemDamage());
        else if(currItem.getItem().getUnlocalizedName().toLowerCase().contains("horsearmor")) return new ItemStack(Items.golden_horse_armor, currItem.stackSize, 0);
        else if(currItem.getItem().getUnlocalizedName().toLowerCase().contains("nugget") || currItem.getItem().getItemStackDisplayName(currItem).toLowerCase().contains(" nugget") || currItem.getItem().getItemStackDisplayName(currItem).toLowerCase().contains("nugget ")) return new ItemStack(Items.gold_nugget, currItem.stackSize, 0);
        else if(currItem.getItem().getUnlocalizedName().toLowerCase().contains("ingot") || currItem.getItem().getItemStackDisplayName(currItem).toLowerCase().contains(" ingot") || currItem.getItem().getItemStackDisplayName(currItem).toLowerCase().contains("ingot ")) return new ItemStack(Items.gold_ingot, currItem.stackSize, 0);
        else if(Block.getBlockFromItem(currItem.getItem()) instanceof BlockPressurePlate || Block.getBlockFromItem(currItem.getItem()) instanceof BlockPressurePlateWeighted) return new ItemStack(Blocks.light_weighted_pressure_plate, currItem.stackSize, 0);
        else if(Block.getBlockFromItem(currItem.getItem()) instanceof BlockCompressed) return new ItemStack(Blocks.gold_block, currItem.stackSize, 0);
        return null;
    }
    
    public boolean isGoldenObject(Item item)
    {
        return item.equals(Items.gold_ingot) || item.equals(Items.gold_nugget) || item.equals(Items.golden_helmet) || item.equals(Items.golden_chestplate) || 
            item.equals(Items.golden_leggings) || item.equals(Items.golden_boots) || item.equals(Items.golden_sword) || item.equals(Items.golden_shovel) || 
            item.equals(Items.golden_pickaxe) || item.equals(Items.golden_axe) || item.equals(Items.golden_hoe) || item.equals(Items.golden_horse_armor) || 
            Block.getBlockFromItem(item).equals(Blocks.gold_block) || Block.getBlockFromItem(item).equals(Blocks.light_weighted_pressure_plate);
    }
    
    public String getDescription()
    {
        return "Be careful what you wish for...";
    }
}