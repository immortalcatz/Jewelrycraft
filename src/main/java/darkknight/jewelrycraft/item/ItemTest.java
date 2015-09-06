package darkknight.jewelrycraft.item;

import java.util.List;
import com.pahimar.repackage.cofh.lib.util.helpers.MathHelper;
import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.util.Variables;

public class ItemTest extends Item
{
    @SideOnly (Side.CLIENT)
    private IIcon[] icons;
    private NBTTagCompound nbt = new NBTTagCompound();
    private TileEntity test = new TileEntity();
    
    public ItemTest()
    {
        super();
        setHasSubtypes(true);
    }
    
    @Override
    @SideOnly (Side.CLIENT)
    public IIcon getIconFromDamage(int damage)
    {
        int j = MathHelper.clamp_int(damage, 0, Curse.getCurseList().size() - 1);
        return icons[j];
    }
    
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for(int j = 0; j < Curse.getCurseList().size(); ++j)
            par3List.add(new ItemStack(par1, 1, j));
    }
    
    public void registerIcons(IIconRegister par1IconRegister)
    {
    	icons = new IIcon[Curse.getCurseList().size()];
        for(int i = 0; i < Curse.getCurseList().size(); ++i)
        	icons[i] = par1IconRegister.registerIcon(Variables.MODID + ":" + "testItem_" + i);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		MovingObjectPosition mop = getMovingObjectPositionFromPlayer(world, player, true);
		if (mop != null && mop.typeOfHit == MovingObjectType.BLOCK) {
			int x = mop.blockX, y = mop.blockY, z = mop.blockZ;
			TileEntity tile = world.getTileEntity(x, y, z);
	        if(tile != null)
	        	if(!player.isSneaking()) tile.writeToNBT(nbt);
	        	else{
	        		NBTTagCompound block = new NBTTagCompound();
	        		tile.writeToNBT(block);
		        	nbt.setString("id", block.getString("id"));
		        	nbt.setInteger("x", tile.xCoord);
		        	nbt.setInteger("y", tile.yCoord);
		        	nbt.setInteger("z", tile.zCoord);
		        	tile.readFromNBT(nbt);
		            world.func_147479_m(x, y, z);
	        	}
		}
        return stack;
    }
}
