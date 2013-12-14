package bspkrs.briefcasespeakers.config;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler
{
    private static Configuration config;
    public static int            idThiefGloves        = 17493;
    
    public static int            idSpeaker            = 17495;
    public static int            idRemote             = 17496;
    public static int            idShadowIngot        = 17497;
    
    public static int            idBriefcaseSpeakers  = 1749;
    
    public static int            idShadowOre          = 1750;
    public static int            idSmelter            = 1751;
    public static int            idMolder             = 1752;
    public static int            idJewelCraftingTable = 1753;
    
    private static boolean       isInitialized        = false;
    
    public static void preInit(FMLPreInitializationEvent e)
    {
        if (!isInitialized)
        {
            config = new Configuration(e.getSuggestedConfigurationFile());
            
            config.load();
            
            // Blocks
            idBriefcaseSpeakers = config.getBlock("id.BriefcaseSpeakers", idBriefcaseSpeakers).getInt();
            
            // Items
            idThiefGloves = config.getItem("id.ThiefGloves", idThiefGloves).getInt();
            idSpeaker = config.getItem("id.Speaker", idSpeaker).getInt();
            idRemote = config.getItem("id.Remote", idRemote).getInt();
            idShadowIngot = config.getItem("id.ShadowIngot", idShadowIngot).getInt();
            idShadowOre = config.getBlock("id.ShadowOre", idShadowOre).getInt();
            idSmelter = config.getBlock("id.Smelter", idSmelter).getInt();
            idMolder = config.getBlock("id.Molder", idMolder).getInt();
            idJewelCraftingTable = config.getBlock("id.JewelCraftingTable", idJewelCraftingTable).getInt();
            
            config.save();
            
            isInitialized = true;
        }
    }
}
