package darkknight.jewelrycraft.block;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import darkknight.jewelrycraft.tileentity.TileEntityMolder;
import darkknight.jewelrycraft.tileentity.TileEntitySmelter;

public class BlockSmelter extends BlockContainer
{
    Random rand = new Random();
    
    protected BlockSmelter(int par1, Material par2Material)
    {
        super(par1, par2Material);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntitySmelter();
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public void dropItem(World world, double x, double y, double z, ItemStack stack)
    {
        EntityItem entityitem = new EntityItem(world, x + 0.5D, y + 1.3D, z + 0.5D, stack);
        entityitem.motionX = 0;
        entityitem.motionZ = 0;
        entityitem.motionY = 0.11000000298023224D;
        world.spawnEntityInWorld(entityitem);
    }
    
    public void breakBlock(World world, int i, int j, int k, int par5, int par6)
    {
        TileEntitySmelter te = (TileEntitySmelter) world.getBlockTileEntity(i, j, k);
        if (te != null && te.hasMetal) dropItem(world, (double)te.xCoord, (double)te.yCoord, (double)te.zCoord, te.metal);
        super.breakBlock(world, i, j, k, par5, par6);
    }
    
    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntitySmelter te = (TileEntitySmelter) world.getBlockTileEntity(i, j, k);
        ItemStack item = entityPlayer.inventory.getCurrentItem();
        if (te != null && !world.isRemote)
        {
            if (!te.hasMetal && !te.hasMoltenMetal && item != null && item.getUnlocalizedName().toLowerCase().contains("ingot") && !item.getDisplayName().contains("Mold"))
            {
                entityPlayer.addChatMessage(StatCollector.translateToLocalFormatted("chatmessage.jewelrycraft.smelter.nowsmeltingingot", item.getDisplayName()));
                te.metal = new ItemStack(item.itemID, 1, item.getItemDamage());
                te.hasMetal = true;
                te.melting = 1500;
                --item.stackSize;
            }
            else if (te.hasMetal && !te.hasMoltenMetal && item != null && item.getDisplayName().contains("Ingot") && !item.getDisplayName().contains("Mold"))
                entityPlayer.addChatMessage(StatCollector.translateToLocalFormatted("chatmessage.jewelrycraft.smelter.alreadyhasingot", te.metal.getDisplayName()));
            else if (te.hasMoltenMetal)
                entityPlayer.addChatMessage(StatCollector.translateToLocalFormatted("chatmessage.jewelrycraft.smelter.hasmolteningot", te.moltenMetal.getDisplayName()));
            else if (item != null && !item.getUnlocalizedName().toLowerCase().contains("ingot") && item.getDisplayName().contains("Ingot"))
                entityPlayer.addChatMessage(StatCollector.translateToLocal("chatmessage.jewelrycraft.smelter.itemrenamedtoingot"));
            else if (item != null && !item.getUnlocalizedName().toLowerCase().contains("ingot"))
                entityPlayer.addChatMessage(StatCollector.translateToLocal("chatmessage.jewelrycraft.smelter.itemneedstobeingot"));
            
            if (te.hasMetal && entityPlayer.isSneaking())
            {
                dropItem(world, (double)te.xCoord, (double)te.yCoord, (double)te.zCoord, te.metal);
                te.hasMetal = false;
            }
            world.setBlockTileEntity(i, j, k, te);
            te.isDirty = true;
        }
        return true;
    }
    
    @Override
    public void onBlockClicked(World world, int i, int j, int k, EntityPlayer player)
    {
        TileEntitySmelter te = (TileEntitySmelter) world.getBlockTileEntity(i, j, k);
        TileEntityMolder me = null;
        if (world.getBlockMetadata(i, j, k) == 0)
            me = (TileEntityMolder) world.getBlockTileEntity(i, j, k - 1);
        else if (world.getBlockMetadata(i, j, k) == 1)
            me = (TileEntityMolder) world.getBlockTileEntity(i + 1, j, k);
        else if (world.getBlockMetadata(i, j, k) == 2)
            me = (TileEntityMolder) world.getBlockTileEntity(i, j, k + 1);
        else if (world.getBlockMetadata(i, j, k) == 3)
            me = (TileEntityMolder) world.getBlockTileEntity(i - 1, j, k);
        
        if (te.hasMoltenMetal && me != null && !world.isRemote)
        {
            if (isConnectedToMolder(world, i, j, k) && me.hasMold && !me.hasMoltenMetal && !me.hasJewelBase)
            {
                me.moltenMetal = te.moltenMetal;
                me.hasMoltenMetal = true;
                me.cooling = 200;
                te.moltenMetal = new ItemStack(0, 0, 0);
                te.hasMoltenMetal = false;
                me.isDirty = true;
            }
            else if (me.hasMoltenMetal)
                player.addChatMessage(StatCollector.translateToLocal("chatmessage.jewelrycraft.smelter.molderhasmoltenmetal"));
            else if (!me.hasMold)
                player.addChatMessage(StatCollector.translateToLocal("chatmessage.jewelrycraft.smelter.molderhasnomold"));
            else if (me.hasJewelBase)
                player.addChatMessage(StatCollector.translateToLocal("chatmessage.jewelrycraft.smelter.modlerhasitem"));
            else
                player.addChatMessage(StatCollector.translateToLocal("chatmessage.jewelrycraft.smelter.molderismissing"));
            te.isDirty = true;
        }
        
    }
    
    public boolean isConnectedToMolder(World world, int i, int j, int k)
    {
        int blockMeta = world.getBlockMetadata(i, j, k);
        if (blockMeta == 0 && world.getBlockId(i, j, k - 1) == BlockList.molder.blockID)
            return true;
        else if (blockMeta == 1 && world.getBlockId(i + 1, j, k) == BlockList.molder.blockID)
            return true;
        else if (blockMeta == 2 && world.getBlockId(i, j, k + 1) == BlockList.molder.blockID)
            return true;
        else if (blockMeta == 3 && world.getBlockId(i - 1, j, k) == BlockList.molder.blockID)
            return true;
        return false;
    }
    
    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityLiving, ItemStack par6ItemStack)
    {
        int rotation = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        world.setBlockMetadataWithNotify(i, j, k, rotation, 2);
    }
    
    @Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @Override
    public int getRenderType()
    {
        return -1;
    }
    
    @Override
    public void registerIcons(IconRegister icon)
    {
        this.blockIcon = icon.registerIcon("jewelrycraft:smelter");
    }
    
}
