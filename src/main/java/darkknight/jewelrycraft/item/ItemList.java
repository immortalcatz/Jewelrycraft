package darkknight.jewelrycraft.item;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemList
{
    public static Item thiefGloves;
    public static Item shadowIngot;
    public static Item molds;
    public static Item clayMolds;
    public static ItemRing ring;
    public static ItemNecklace necklace;
    public static ItemBracelet bracelet;
    public static ItemEarrings earrings;
    public static Item guide;
    public static Item jewelryModifier;
    public static ItemMoltenMetalBucket bucket;
    public static ItemMoltenMetal metal;
    public static Item goldObj;
    public static Item structureGen;
    public static Item spawnEgg;
    public static Item testItem;
    
    /**
     * @param e
     */
    public static void preInit(FMLPreInitializationEvent e)
    {
        thiefGloves = new ItemThiefGloves().setUnlocalizedName(Variables.MODID + ".thiefGloves").setTextureName(Variables.MODID + ":thiefGloves").setCreativeTab(JewelrycraftMod.jewelrycraft);
        shadowIngot = new Item().setUnlocalizedName(Variables.MODID + ".ingotShadow").setTextureName(Variables.MODID + ":ingotShadow").setCreativeTab(JewelrycraftMod.jewelrycraft);
        molds = new ItemMolds().setUnlocalizedName(Variables.MODID + ".mold").setTextureName("Mold").setCreativeTab(JewelrycraftMod.jewelrycraft);
        clayMolds = new ItemClayMolds().setUnlocalizedName(Variables.MODID + ".mold").setTextureName("Mold").setCreativeTab(JewelrycraftMod.jewelrycraft);
        ring = (ItemRing)new ItemRing().setUnlocalizedName(Variables.MODID + ".ring").setTextureName(Variables.MODID + ":ring").setCreativeTab(JewelrycraftMod.jewelrycraft);
        necklace = (ItemNecklace)new ItemNecklace().setUnlocalizedName(Variables.MODID + ".necklace").setTextureName(Variables.MODID + ":necklace").setCreativeTab(JewelrycraftMod.jewelrycraft);
        bracelet = (ItemBracelet)new ItemBracelet().setUnlocalizedName(Variables.MODID + ".bracelet").setTextureName(Variables.MODID + ":bracelet").setCreativeTab(JewelrycraftMod.jewelrycraft);
        earrings = (ItemEarrings)new ItemEarrings().setUnlocalizedName(Variables.MODID + ".earrings").setTextureName(Variables.MODID + ":earrings").setCreativeTab(JewelrycraftMod.jewelrycraft);
        guide = new ItemGuide().setUnlocalizedName(Variables.MODID + ".guide").setTextureName(Variables.MODID + ":guide").setCreativeTab(JewelrycraftMod.jewelrycraft);
        bucket = (ItemMoltenMetalBucket)new ItemMoltenMetalBucket().setUnlocalizedName(Variables.MODID + ".bucket");
        metal = (ItemMoltenMetal)new ItemMoltenMetal().setUnlocalizedName(Variables.MODID + ".bucket");
        jewelryModifier = new ItemJewelryModifier().setUnlocalizedName(Variables.MODID + ".jewelryModifier").setTextureName(Variables.MODID + ":jewelryModifier").setCreativeTab(JewelrycraftMod.jewelrycraft);
        goldObj = new ItemGoldObj().setUnlocalizedName(Variables.MODID + ".goldObject");
        structureGen = new ItemStructureGen().setUnlocalizedName(Variables.MODID + ".structureGen").setTextureName(Variables.MODID + ":structureGen").setCreativeTab(JewelrycraftMod.jewelrycraft);
        spawnEgg = new ItemSpawnEgg().setUnlocalizedName(Variables.MODID + ".monsterPlacer").setTextureName("spawn_egg");
        testItem = new ItemTest().setUnlocalizedName(Variables.MODID + ".test");
        
        GameRegistry.registerItem(thiefGloves, "thiefGloves");
        GameRegistry.registerItem(shadowIngot, "shadowIngot");
        GameRegistry.registerItem(molds, "molds");
        GameRegistry.registerItem(clayMolds, "clayMolds");
        GameRegistry.registerItem(ring, "ring");
        GameRegistry.registerItem(necklace, "necklace");
        GameRegistry.registerItem(bracelet, "bracelet");
        GameRegistry.registerItem(earrings, "earrings");
        GameRegistry.registerItem(guide, "guide");
        GameRegistry.registerItem(bucket, "moltenMetalBucket");
        GameRegistry.registerItem(metal, "moltenMetal");
        GameRegistry.registerItem(jewelryModifier, "jewelryModifier");
        GameRegistry.registerItem(goldObj, "goldObject");
        GameRegistry.registerItem(structureGen, "structureGen");
        GameRegistry.registerItem(spawnEgg, "spawnEgg");
        GameRegistry.registerItem(testItem, "testItem");
        
        OreDictionary.registerOre("ingotShadow", new ItemStack(ItemList.shadowIngot));
    }
}
