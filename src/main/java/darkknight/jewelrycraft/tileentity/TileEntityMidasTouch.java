package darkknight.jewelrycraft.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMidasTouch extends TileEntity
{
    public Entity target;
    
    public TileEntityMidasTouch()
    {}
    
    public void setEntity(Entity ent)
    {
        target = ent;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        if (target != null){
            int id = EntityList.getEntityID(target);
            NBTTagCompound tag = new NBTTagCompound();
            target.writeToNBT(tag);
            nbt.setInteger("entityID", id);
            nbt.setTag("entity", tag);
        }
    }
    
    /**
     * @param nbt
     */
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        NBTTagCompound en = (NBTTagCompound)nbt.getTag("entity");
        int entityID = nbt.getInteger("entityID");
        EntityLivingBase entity = (EntityLivingBase)EntityList.createEntityByID(entityID, worldObj);
        if (entity != null){
            entity.readFromNBT(en);
            target = entity;
        }
    }
    
    /**
     * 
     */
    @Override
    public boolean canUpdate()
    {
        return false;
    }
    
    /**
     * @return
     */
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbttagcompound);
    }
    
    /**
     * @param net
     * @param packet
     */
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
    {
        readFromNBT(packet.func_148857_g());
        worldObj.func_147479_m(xCoord, yCoord, zCoord);
    }
}
