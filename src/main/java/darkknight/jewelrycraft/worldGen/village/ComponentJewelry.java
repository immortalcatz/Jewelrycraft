package darkknight.jewelrycraft.worldGen.village;

import java.util.List;
import java.util.Random;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.item.ItemMolds;
import darkknight.jewelrycraft.tileentity.TileEntityDisplayer;
import darkknight.jewelrycraft.tileentity.TileEntityMolder;
import darkknight.jewelrycraft.tileentity.TileEntitySmelter;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;

public class ComponentJewelry extends StructureVillagePieces.House1 {
	private int	averageGroundLevel	= -1;

	public ComponentJewelry() {
	}

	public ComponentJewelry(Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5) {
		super();
		coordBaseMode = par5;
		boundingBox = par4StructureBoundingBox;
	}

	@SuppressWarnings("rawtypes")
	public static ComponentJewelry buildComponent(Start villagePiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5) {
		StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p1, p2, p3, 0, 0, 0, 11, 5, 12, p4);
		return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new ComponentJewelry(villagePiece, p5, random, structureboundingbox, p4) : null;
	}

	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox sbb) {
		if (averageGroundLevel < 0) {
			averageGroundLevel = getAverageGroundLevel(world, sbb);
			if (averageGroundLevel < 0) return true;
			boundingBox.offset(0, averageGroundLevel - boundingBox.maxY + 3, 0);
		}
		fillWithBlocks(world, sbb, 0, 0, 6, 10, 5, 11, Block.getBlockById(0), Block.getBlockById(0), false);
		fillWithBlocks(world, sbb, 2, 0, 0, 8, 5, 5, Block.getBlockById(0), Block.getBlockById(0), false);
		// Pillars
		fillWithBlocks(world, sbb, 2, 0, 0, 2, 3, 0, Blocks.log, Blocks.log, false);
		fillWithBlocks(world, sbb, 2, 0, 3, 2, 3, 3, Blocks.log, Blocks.log, false);
		fillWithBlocks(world, sbb, 8, 0, 0, 8, 3, 0, Blocks.log, Blocks.log, false);
		fillWithBlocks(world, sbb, 8, 0, 3, 8, 3, 3, Blocks.log, Blocks.log, false);
		// Walls
		fillWithBlocks(world, sbb, 2, 0, 1, 2, 3, 2, Blocks.planks, Blocks.planks, false);
		fillWithBlocks(world, sbb, 2, 0, 4, 2, 3, 5, Blocks.planks, Blocks.planks, false);
		fillWithBlocks(world, sbb, 8, 0, 1, 8, 3, 2, Blocks.planks, Blocks.planks, false);
		fillWithBlocks(world, sbb, 8, 0, 4, 8, 3, 5, Blocks.planks, Blocks.planks, false);
		fillWithBlocks(world, sbb, 3, 0, 0, 7, 3, 0, Blocks.planks, Blocks.planks, false);
		fillWithBlocks(world, sbb, 0, 0, 6, 10, 3, 6, Blocks.cobblestone, Blocks.cobblestone, false);
		fillWithBlocks(world, sbb, 0, 0, 11, 10, 3, 11, Blocks.cobblestone, Blocks.cobblestone, false);
		fillWithBlocks(world, sbb, 0, 0, 6, 0, 3, 11, Blocks.cobblestone, Blocks.cobblestone, false);
		fillWithBlocks(world, sbb, 10, 0, 6, 10, 3, 11, Blocks.cobblestone, Blocks.cobblestone, false);
		// Roof
		for (int i = 3; i <= 7; i++)
			for (int j = 1; j <= 5; j++)
				placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 2, i, 4, j, sbb);
		for (int i = 3; i <= 7; i++)
			for (int j = 6; j <= 6; j++)
				placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, i, 4, j, sbb);
		for (int i = 1; i <= 9; i++)
			for (int j = 7; j <= 10; j++)
				placeBlockAtCurrentPosition(world, Blocks.stone_slab, 3, i, 4, j, sbb);
		for (int i = 2; i <= 8; i++)
			placeBlockAtCurrentPosition(world, Blocks.double_wooden_slab, 2, i, 4, 0, sbb);
		for (int i = 1; i <= 5; i++) {
			placeBlockAtCurrentPosition(world, Blocks.double_wooden_slab, 2, 2, 4, i, sbb);
			placeBlockAtCurrentPosition(world, Blocks.double_wooden_slab, 2, 8, 4, i, sbb);
		}
		for (int i = 0; i <= 2; i++) {
			placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, i, 4, 6, sbb);
			placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, i + 8, 4, 6, sbb);
		}
		for (int i = 7; i <= 11; i++) {
			placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 0, 4, i, sbb);
			placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 10, 4, i, sbb);
		}
		for (int i = 0; i <= 10; i++)
			placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, i, 4, 11, sbb);
		// Base
		for (int i = 2; i <= 8; i++)
			for (int j = 0; j <= 5; j++)
				placeBlockAtCurrentPosition(world, Blocks.planks, 1, i, 0, j, sbb);
		fillWithBlocks(world, sbb, 0, 0, 6, 10, 0, 11, Blocks.stonebrick, Blocks.stonebrick, false);
		for (int i = 6; i <= 10; i++)
			placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 5, 0, i, sbb);
		for (int i = 7; i <= 10; i++) {
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 1, 0, i, sbb);
			placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 9, 0, i, sbb);
		}
		// Decorations
		placeDoorAtCurrentPosition(world, sbb, random, 6, 1, 0, getMetadataWithOffset(Blocks.wooden_door, 1));
		placeDoorAtCurrentPosition(world, sbb, random, 5, 1, 6, getMetadataWithOffset(Blocks.wooden_door, 1));
		placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 3, 2, 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 4, 2, 0, sbb);
		placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 2, 2, 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 2, 2, 2, sbb);
		placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 2, 2, 4, sbb);
		placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 2, 2, 5, sbb);
		placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 8, 2, 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 8, 2, 2, sbb);
		placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 8, 2, 4, sbb);
		placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 8, 2, 5, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 6, 3, 1, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 3, 3, 3, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 7, 3, 3, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 5, 3, 5, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 5, 3, 7, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 5, 3, 10, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 1, 3, 8, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 1, 3, 9, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 9, 3, 8, sbb);
		placeBlockAtCurrentPosition(world, Blocks.torch, 0, 9, 3, 9, sbb);
		int bgCarpetColor = random.nextInt(16);
		for (int i = 4; i <= 7; i++)
			for (int j = 1; j <= 5; j++)
				placeBlockAtCurrentPosition(world, Blocks.carpet, bgCarpetColor, i, 1, j, sbb);
		generateChest(world, 3, 1, 1, 0, random, sbb, ConfigHandler.GEM_CHEST_MIN, ConfigHandler.GEM_CHEST_MAX);
		generateDisplayer(world, 3, 1, 2, coordBaseMode == 0 || coordBaseMode == 2 ? 1 : 2, random, sbb);
		placeBlockAtCurrentPosition(world, BlockList.jewelCraftingTable, coordBaseMode == 0 || coordBaseMode == 2 ? 1 : 2, 3, 1, 3, sbb);
		generateDisplayer(world, 3, 1, 4, coordBaseMode == 0 || coordBaseMode == 2 ? 1 : 2, random, sbb);
		generateChest(world, 3, 1, 5, 0, random, sbb, ConfigHandler.GEM_CHEST_MIN, ConfigHandler.GEM_CHEST_MAX);
		generateFurnace(world, 1, 1, 7, 0, random, sbb, ConfigHandler.FURNACE_MIN_INGOT_STACK, ConfigHandler.FURNACE_MAX_INGOT_STACK, ConfigHandler.CAN_FURNACE_GENERATE_INGOTS);
		generateFurnace(world, 1, 2, 7, 0, random, sbb, ConfigHandler.FURNACE_MIN_INGOT_STACK, ConfigHandler.FURNACE_MAX_INGOT_STACK, ConfigHandler.CAN_FURNACE_GENERATE_INGOTS);
		generateFurnace(world, 1, 3, 7, 0, random, sbb, ConfigHandler.FURNACE_MIN_INGOT_STACK, ConfigHandler.FURNACE_MAX_INGOT_STACK, ConfigHandler.CAN_FURNACE_GENERATE_INGOTS);
		generateFurnace(world, 1, 1, 10, 0, random, sbb, ConfigHandler.FURNACE_MIN_INGOT_STACK, ConfigHandler.FURNACE_MAX_INGOT_STACK, ConfigHandler.CAN_FURNACE_GENERATE_INGOTS);
		generateFurnace(world, 1, 2, 10, 0, random, sbb, ConfigHandler.FURNACE_MIN_INGOT_STACK, ConfigHandler.FURNACE_MAX_INGOT_STACK, ConfigHandler.CAN_FURNACE_GENERATE_INGOTS);
		generateFurnace(world, 1, 3, 10, 0, random, sbb, ConfigHandler.FURNACE_MIN_INGOT_STACK, ConfigHandler.FURNACE_MAX_INGOT_STACK, ConfigHandler.CAN_FURNACE_GENERATE_INGOTS);
		generateSmelter(world, 1, 1, 8, coordBaseMode == 0 || coordBaseMode == 2 ? 1 : 2, random, sbb, random.nextBoolean());
		generateSmelter(world, 1, 1, 9, coordBaseMode == 0 || coordBaseMode == 2 ? 1 : 2, random, sbb, random.nextBoolean());
		generateMolder(world, 2, 1, 8, coordBaseMode == 0 || coordBaseMode == 2 ? 1 : 2, random, sbb, random.nextBoolean(), random.nextBoolean());
		generateMolder(world, 2, 1, 9, coordBaseMode == 0 || coordBaseMode == 2 ? 1 : 2, random, sbb, random.nextBoolean(), random.nextBoolean());
		if (random.nextBoolean()) generateIngotChest(world, 9, 1, 7, 0, random, sbb, ConfigHandler.INGOT_CHEST_MIN, ConfigHandler.INGOT_CHEST_MAX, Blocks.chest, ConfigHandler.INGOT_CHEST_MAX_STACK);
		else generateOresChest(world, 9, 1, 7, 0, random, sbb, ConfigHandler.INGOT_CHEST_MIN, ConfigHandler.INGOT_CHEST_MAX, Blocks.chest, ConfigHandler.INGOT_CHEST_MAX_STACK);
		if (random.nextBoolean()) generateIngotChest(world, 9, 1, 8, 0, random, sbb, ConfigHandler.INGOT_CHEST_MIN, ConfigHandler.INGOT_CHEST_MAX, Blocks.chest, ConfigHandler.INGOT_CHEST_MAX_STACK);
		else generateOresChest(world, 9, 1, 8, 0, random, sbb, ConfigHandler.INGOT_CHEST_MIN, ConfigHandler.INGOT_CHEST_MAX, Blocks.chest, ConfigHandler.INGOT_CHEST_MAX_STACK);
		if (random.nextBoolean()) generateIngotChest(world, 9, 1, 9, 0, random, sbb, ConfigHandler.INGOT_CHEST_MIN, ConfigHandler.INGOT_CHEST_MAX, Blocks.trapped_chest, ConfigHandler.INGOT_CHEST_MAX_STACK);
		else generateOresChest(world, 9, 1, 9, 0, random, sbb, ConfigHandler.INGOT_CHEST_MIN, ConfigHandler.INGOT_CHEST_MAX, Blocks.trapped_chest, ConfigHandler.INGOT_CHEST_MAX_STACK);
		if (random.nextBoolean()) generateIngotChest(world, 9, 1, 10, 0, random, sbb, ConfigHandler.INGOT_CHEST_MIN, ConfigHandler.INGOT_CHEST_MAX, Blocks.trapped_chest, ConfigHandler.INGOT_CHEST_MAX_STACK);
		else generateOresChest(world, 9, 1, 10, 0, random, sbb, ConfigHandler.INGOT_CHEST_MIN, ConfigHandler.INGOT_CHEST_MAX, Blocks.trapped_chest, ConfigHandler.INGOT_CHEST_MAX_STACK);
		for (int l = 0; l < 6; ++l)
			for (int i1 = 2; i1 < 9; ++i1) {
				clearCurrentPositionBlocksUpwards(world, i1, 9, l, sbb);
				func_151554_b(world, Blocks.cobblestone, 0, i1, -1, l, sbb);
			}
		for (int l = 6; l < 12; ++l)
			for (int i1 = 0; i1 < 11; ++i1) {
				clearCurrentPositionBlocksUpwards(world, i1, 9, l, sbb);
				func_151554_b(world, Blocks.cobblestone, 0, i1, -1, l, sbb);
			}
		spawnVillagers(world, sbb, 3, 1, 3, 1);
		return true;
	}

	public void generateChest(World world, int i, int j, int k, int metadata, Random random, StructureBoundingBox sbb, int min, int max) {
		int i1 = getXWithOffset(i, k);
		int j1 = getYWithOffset(j);
		int k1 = getZWithOffset(i, k);
		if (max >= min) {
			int t = random.nextInt(max - min + 1) + min;
			placeBlockAtCurrentPosition(world, Blocks.chest, metadata, i, j, k, sbb);
			if (world.getTileEntity(i1, j1, k1) != null) {
				TileEntityChest chest = (TileEntityChest) world.getTileEntity(i1, j1, k1);
				while (chest != null && t > 0 && JewelrycraftUtil.gem.size() > 0) {
					ItemStack jewels = JewelrycraftUtil.gem.get(random.nextInt(JewelrycraftUtil.gem.size()));
					chest.func_145976_a(StatCollector.translateToLocal("jeweler." + Variables.MODID + ".jewelerchest"));
					if (jewels.getItem() == Items.nether_star && ConfigHandler.GENERATE_VILLAGE_NETHERSTAR) chest.setInventorySlotContents(random.nextInt(chest.getSizeInventory()), jewels);
					else if (random.nextBoolean() && jewels.getItem() != Items.nether_star) chest.setInventorySlotContents(random.nextInt(chest.getSizeInventory()), jewels);
					t--;
				}
			}
		}
	}

	public void generateIngotChest(World world, int i, int j, int k, int metadata, Random random, StructureBoundingBox sbb, int min, int max, Block chestB, int randomAmount) {
		int i1 = getXWithOffset(i, k);
		int j1 = getYWithOffset(j);
		int k1 = getZWithOffset(i, k);
		if (max >= min) {
			int t = random.nextInt(max - min + 1) + min;
			placeBlockAtCurrentPosition(world, chestB, metadata, i, j, k, sbb);
			if (world.getTileEntity(i1, j1, k1) != null) {
				TileEntityChest chest = (TileEntityChest) world.getTileEntity(i1, j1, k1);
				while (chest != null && t > 0 && JewelrycraftUtil.metal.size() > 0) {
					chest.func_145976_a(StatCollector.translateToLocal("jeweler." + Variables.MODID + ".ingotchest"));
					int metalID = random.nextInt(JewelrycraftUtil.metal.size());
					ItemStack metal = JewelrycraftUtil.metal.get(metalID).copy();
					metal.stackSize = 2 + random.nextInt(randomAmount);
					if (random.nextBoolean()) chest.setInventorySlotContents(random.nextInt(chest.getSizeInventory()), metal);
					t--;
				}
			}
		}
	}

	public void generateOresChest(World world, int i, int j, int k, int metadata, Random random, StructureBoundingBox sbb, int min, int max, Block chestB, int randomAmount) {
		int i1 = getXWithOffset(i, k);
		int j1 = getYWithOffset(j);
		int k1 = getZWithOffset(i, k);
		if (max >= min) {
			int t = random.nextInt(max - min + 1) + min;
			placeBlockAtCurrentPosition(world, chestB, metadata, i, j, k, sbb);
			if (world.getTileEntity(i1, j1, k1) != null) {
				TileEntityChest chest = (TileEntityChest) world.getTileEntity(i1, j1, k1);
				while (chest != null && t > 0 && JewelrycraftUtil.ores.size() > 0) {
					chest.func_145976_a(StatCollector.translateToLocal("jeweler." + Variables.MODID + ".orechest"));
					int oreID = random.nextInt(JewelrycraftUtil.ores.size());
					ItemStack ores = JewelrycraftUtil.ores.get(oreID).copy();
					ores.stackSize = 2 + random.nextInt(randomAmount);
					if (random.nextBoolean()) chest.setInventorySlotContents(random.nextInt(chest.getSizeInventory()), ores);
					t--;
				}
			}
		}
	}

	public void generateDisplayer(World world, int i, int j, int k, int metadata, Random random, StructureBoundingBox sbb) {
		int i1 = getXWithOffset(i, k);
		int j1 = getYWithOffset(j);
		int k1 = getZWithOffset(i, k);
		placeBlockAtCurrentPosition(world, BlockList.displayer, metadata, i, j, k, sbb);
		if (world.getTileEntity(i1, j1, k1) != null) {
			TileEntityDisplayer displayer = (TileEntityDisplayer) world.getTileEntity(i1, j1, k1);
			if (displayer != null) {
				Item[] jewels = { ItemList.ring, ItemList.necklace };
				ItemStack jewel = new ItemStack(jewels[random.nextInt(jewels.length)]);
				if (JewelrycraftUtil.metal.size() > 0) JewelryNBT.addMetal(jewel, JewelrycraftUtil.metal.get(random.nextInt(JewelrycraftUtil.metal.size())));
				if (JewelrycraftUtil.objects.size() > 0) JewelryNBT.addModifiers(jewel, JewelrycraftUtil.addRandomModifiers(random.nextInt(4)));
				if (JewelrycraftUtil.gem.size() > 0) JewelryNBT.addGem(jewel, JewelrycraftUtil.gem.get(random.nextInt(JewelrycraftUtil.gem.size())));
				displayer.object = jewel;
				displayer.quantity = 1;
				displayer.hasObject = true;
			}
		}
	}

	public void generateSmelter(World world, int i, int j, int k, int metadata, Random random, StructureBoundingBox sbb, boolean isEmpty) {
		int i1 = getXWithOffset(i, k);
		int j1 = getYWithOffset(j);
		int k1 = getZWithOffset(i, k);
		placeBlockAtCurrentPosition(world, BlockList.smelter, metadata, i, j, k, sbb);
		if (world.getTileEntity(i1, j1, k1) != null) {
			TileEntitySmelter smelter = (TileEntitySmelter) world.getTileEntity(i1, j1, k1);
			if (smelter != null && !isEmpty && JewelrycraftUtil.metal.size() > 0) {
				int metal = random.nextInt(JewelrycraftUtil.metal.size());
				smelter.moltenMetal = JewelrycraftUtil.metal.get(metal).copy();
				smelter.hasMoltenMetal = random.nextBoolean();
				float quantity = random.nextFloat();
				if (smelter.hasMoltenMetal) smelter.quantity = quantity < 0.9F ? 0.1F + Math.round(quantity * 10) / 10 : Math.round(quantity * 10) / 10;
			}
		}
	}

	public void generateMolder(World world, int i, int j, int k, int metadata, Random random, StructureBoundingBox sbb, boolean hasMold, boolean hasStuff) {
		int i1 = getXWithOffset(i, k);
		int j1 = getYWithOffset(j);
		int k1 = getZWithOffset(i, k);
		placeBlockAtCurrentPosition(world, BlockList.molder, metadata, i, j, k, sbb);
		if (world.getTileEntity(i1, j1, k1) != null) {
			TileEntityMolder molder = (TileEntityMolder) world.getTileEntity(i1, j1, k1);
			if (molder != null && !molder.hasMold) if (hasMold) {
				int meta = random.nextInt(ItemMolds.moldsItemNames.length);
				molder.mold = new ItemStack(ItemList.molds, 1, meta);
				molder.hasMold = true;
				if (hasStuff && JewelrycraftUtil.metal.size() > 0) {
					ItemStack ring = new ItemStack(ItemList.ring);
					JewelryNBT.addMetal(ring, JewelrycraftUtil.metal.get(random.nextInt(JewelrycraftUtil.metal.size())).copy());
					ItemStack necklace = new ItemStack(ItemList.necklace);
					JewelryNBT.addMetal(necklace, JewelrycraftUtil.metal.get(random.nextInt(JewelrycraftUtil.metal.size())).copy());
					ItemStack bracelet = new ItemStack(ItemList.bracelet);
					JewelryNBT.addMetal(bracelet, JewelrycraftUtil.metal.get(random.nextInt(JewelrycraftUtil.metal.size())).copy());
					ItemStack earrings = new ItemStack(ItemList.earrings);
					JewelryNBT.addMetal(earrings, JewelrycraftUtil.metal.get(random.nextInt(JewelrycraftUtil.metal.size())).copy());
					if (meta == 0) molder.jewelBase = JewelrycraftUtil.metal.get(random.nextInt(JewelrycraftUtil.metal.size()));
					else if (meta == 1) molder.jewelBase = ring;
					else if (meta == 2) molder.jewelBase = necklace;
					else if (meta == 3) molder.jewelBase = bracelet;
					else if (meta == 4) molder.jewelBase = earrings;
					molder.hasJewelBase = true;
				}
			}
		}
	}

	public void generateFurnace(World world, int i, int j, int k, int metadata, Random random, StructureBoundingBox sbb, int min, int max, boolean hasMetal) {
		int i1 = getXWithOffset(i, k);
		int j1 = getYWithOffset(j);
		int k1 = getZWithOffset(i, k);
		placeBlockAtCurrentPosition(world, Blocks.furnace, metadata, i, j, k, sbb);
		if (world.getTileEntity(i1, j1, k1) != null) {
			TileEntityFurnace furnace = (TileEntityFurnace) world.getTileEntity(i1, j1, k1);
			if (furnace != null) {
				if (random.nextBoolean()) furnace.setInventorySlotContents(1, new ItemStack(Items.coal, 1 + random.nextInt(16)));
				if (hasMetal && JewelrycraftUtil.metal.size() > 0) {
					int metalID = random.nextInt(JewelrycraftUtil.metal.size());
					ItemStack metal = JewelrycraftUtil.metal.get(metalID).copy();
					metal.stackSize = random.nextInt(max - min + 1) + min;
					furnace.setInventorySlotContents(2, metal);
				}
			}
		}
	}

	@Override
	protected int getVillagerType(int par1) {
		return 3000;
	}
}