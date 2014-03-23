package darkknight.jewelrycraft.tileentity;

import java.util.List;

import darkknight.jewelrycraft.util.JewelryNBT;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityAltar extends TileEntity
{
    public ItemStack object;
    public boolean isDirty, hasObject;
    public String playerName;

    public TileEntityAltar()
    {
        this.hasObject = false;
        this.object = new ItemStack(0, 0, 0);
        this.isDirty = false;
        this.playerName = "";
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        NBTTagCompound tag = new NBTTagCompound();
        this.object.writeToNBT(tag);
        nbt.setCompoundTag("object", tag);
        nbt.setBoolean("hasObject", hasObject);
        nbt.setString("playerName", playerName);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.object = new ItemStack(0, 0, 0);
        this.object.readFromNBT(nbt.getCompoundTag("object"));
        this.hasObject = nbt.getBoolean("hasObject");
        this.playerName = nbt.getString("playerName");
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        
        if(isDirty)
        {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            isDirty = true;
        }
        if(hasObject && playerName != "")
        {
            //int playerPosX = (int)player.posX, playerPosY = (int)player.posY, playerPosZ = (int)player.posZ;
            List entitiesR = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1D, zCoord, xCoord + 0.5D, yCoord + 2D, zCoord + 0.5D));
            List entitiesN = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(xCoord - 0.5D, yCoord + 1D, zCoord - 0.5D, xCoord + 1.5D, yCoord + 2D, zCoord + 1.5D));
            if(object.getUnlocalizedName().contains("ring"))
            {
                if (JewelryNBT.isJewelX(object, new ItemStack(Item.enderPearl)) && JewelryNBT.dimension(object) != -2 && JewelryNBT.playerPosX(object) != -1 && JewelryNBT.playerPosY(object) != -1 && JewelryNBT.playerPosZ(object) != -1)
                {
                    double posX = JewelryNBT.playerPosX(object), posY = JewelryNBT.playerPosY(object), posZ = JewelryNBT.playerPosZ(object);
                    int dimension = JewelryNBT.dimension(object);
                    for(int i = 0; i < entitiesR.size(); i++)
                    {
                        EntityLivingBase entity = (EntityLivingBase) entitiesR.get(i);
                        if (!JewelryNBT.isDimensionX(object, entity.dimension) && JewelryNBT.isModifierX(object, new ItemStack(Item.bed))){
                            entity.travelToDimension(dimension);
                            entity.setPositionAndUpdate(posX, posY, posZ);
                        }
                        else if(JewelryNBT.isDimensionX(object, entity.dimension)) entity.setPositionAndUpdate(posX, posY, posZ);
                    }
                }
                if (JewelryNBT.isModifierX(object, new ItemStack(Item.dyePowder, 1, 15)))
                {
                    for(int i=-1; i<=1; i++)
                        for(int j=-1; j<=1; j++)
                            for(int k=-1; k<=1; k++)
                            {
                                //if(worldObj.getBlockId(xCoord + i, yCoord + j, zCoord + k) == Block.dirt.blockID && (worldObj.getBlockId(xCoord + i, yCoord + j + 1, zCoord + k) == 0 || worldObj.getBlockId(xCoord + i, yCoord + j + 1, zCoord + k) == Block.crops.blockID) || worldObj.getBlockId(xCoord + i, yCoord + j + 1, zCoord + k) == Block.potato.blockID) worldObj.setBlock(xCoord + i, yCoord + j, zCoord + k, Block.tilledField.blockID);
                                if(worldObj.getBlockId(xCoord + i, yCoord + j, zCoord + k) == Block.tilledField.blockID) worldObj.setBlockMetadataWithNotify(xCoord + i, yCoord + j, zCoord + k, 1, 7); 
                                if(worldObj.getBlockId(xCoord + i, yCoord + j, zCoord + k) != Block.tilledField.blockID) worldObj.scheduleBlockUpdate(xCoord + i, yCoord + j, zCoord + k, worldObj.getBlockId(xCoord + i, yCoord + j, zCoord + k), 5);
                                //JewelrycraftUtil.applyBonemeal(object, worldObj, xCoord + i, yCoord + j, zCoord + k, player);
                            }
                }
            }
            else if(object.getUnlocalizedName().contains("necklace"))
            {
                if (JewelryNBT.isJewelX(object, new ItemStack(Item.enderPearl)) && JewelryNBT.dimension(object) != -2 && JewelryNBT.playerPosX(object) != -1 && JewelryNBT.playerPosY(object) != -1 && JewelryNBT.playerPosZ(object) != -1)
                {
                    double posX = JewelryNBT.playerPosX(object), posY = JewelryNBT.playerPosY(object), posZ = JewelryNBT.playerPosZ(object);
                    int dimension = JewelryNBT.dimension(object);
                    for(int i = 0; i < entitiesN.size(); i++)
                    {
                        EntityLivingBase entity = (EntityLivingBase) entitiesN.get(i);
                        if (!JewelryNBT.isDimensionX(object, entity.dimension) && JewelryNBT.isModifierX(object, new ItemStack(Item.bed))){
                            entity.travelToDimension(dimension);
                            entity.setPositionAndUpdate(posX, posY, posZ);
                        }
                        else if(JewelryNBT.isDimensionX(object, entity.dimension)) entity.setPositionAndUpdate(posX, posY, posZ);
                    }
                }
                if (JewelryNBT.isModifierX(object, new ItemStack(Item.dyePowder, 1, 15)))
                {
                    for(int i=-3; i<=3; i++)
                        for(int j=-1; j<=1; j++)
                            for(int k=-3; k<=3; k++)
                            {
                                //if(worldObj.getBlockId(xCoord + i, yCoord + j, zCoord + k) == Block.dirt.blockID && (worldObj.getBlockId(xCoord + i, yCoord + j + 1, zCoord + k) == 0 || worldObj.getBlockId(xCoord + i, yCoord + j + 1, zCoord + k) == Block.crops.blockID) || worldObj.getBlockId(xCoord + i, yCoord + j + 1, zCoord + k) == Block.potato.blockID) worldObj.setBlock(xCoord + i, yCoord + j, zCoord + k, Block.tilledField.blockID);
                                if(worldObj.getBlockId(xCoord + i, yCoord + j, zCoord + k) == Block.tilledField.blockID) worldObj.setBlockMetadataWithNotify(xCoord + i, yCoord + j, zCoord + k, 1, 7); 
                                if(worldObj.getBlockId(xCoord + i, yCoord + j, zCoord + k) != Block.tilledField.blockID) worldObj.scheduleBlockUpdate(xCoord + i, yCoord + j, zCoord + k, worldObj.getBlockId(xCoord + i, yCoord + j, zCoord + k), 5);
                                //JewelrycraftUtil.applyBonemeal(object, worldObj, xCoord + i, yCoord + j, zCoord + k, player);
                            }
                }
            }            
        }
    }

    @Override
    public Packet getDescriptionPacket() 
    {
        Packet132TileEntityData packet = (Packet132TileEntityData) super.getDescriptionPacket();
        NBTTagCompound dataTag = packet != null ? packet.data : new NBTTagCompound();
        writeToNBT(dataTag);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, dataTag);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) 
    {
        super.onDataPacket(net, pkt);
        NBTTagCompound tag = pkt != null ? pkt.data : new NBTTagCompound();
        readFromNBT(tag);
    }
}
