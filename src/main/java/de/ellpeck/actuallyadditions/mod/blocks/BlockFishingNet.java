/*
 * This file ("BlockFishingNet.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFishingNet;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFishingNet extends BlockContainerBase{

    public BlockFishingNet(String name){
        super(Material.WOOD, name);
        this.setHarvestLevel("axe", 0);
        this.setHardness(0.5F);
        this.setResistance(3.0F);
        this.setSoundType(SoundType.WOOD);
        //TODO Fix block bounds
        //this.setBlockBounds(0F, 0F, 0F, 1F, 1F/16F, 1F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityFishingNet();
    }

    @Override
    public boolean isFullCube(IBlockState state){
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state){
        return false;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }
}
