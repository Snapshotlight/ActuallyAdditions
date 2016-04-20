/*
 * This file ("BlockPlant.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.base;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import java.util.List;
import java.util.Random;

public class BlockPlant extends BlockCrops{

    public Item seedItem;
    public Item returnItem;
    public int returnMeta;
    private String name;
    private int minDropAmount;
    private int addDropAmount;

    public BlockPlant(String name, int minDropAmount, int addDropAmount){
        this.name = name;
        this.minDropAmount = minDropAmount;
        this.addDropAmount = addDropAmount;
        this.register();
    }

    private void register(){
        ItemUtil.registerBlock(this, this.getItemBlock(), this.getBaseName(), this.shouldAddCreative());

        this.registerRendering();
    }

    protected String getBaseName(){
        return this.name;
    }

    protected ItemBlockBase getItemBlock(){
        return new ItemBlockBase(this);
    }

    public boolean shouldAddCreative(){
        return false;
    }

    protected void registerRendering(){
        ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this), new ResourceLocation(ModUtil.MOD_ID, this.getBaseName()));
    }

    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos){
        return EnumPlantType.Crop;
    }

    @Override
    public int damageDropped(IBlockState state){
        return this.getMetaFromState(state) >= 7 ? this.returnMeta : 0;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing facing, float hitX, float hitY, float hitZ){
        if(getMetaFromState(state) >= 7){
            if(!world.isRemote){

                List<ItemStack> drops = getDrops(world, pos, state, 0);
                boolean deductedSeedSize = false;
                for(ItemStack drop : drops){
                    if(drop != null){
                        if(drop.getItem() == this.seedItem && !deductedSeedSize){
                            drop.stackSize--;
                            deductedSeedSize = true;
                        }

                        if(drop.stackSize > 0){
                            EntityItem entity = new EntityItem(world, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, drop);
                            world.spawnEntityInWorld(entity);
                        }
                    }
                }

                world.setBlockState(pos, getStateFromMeta(0));
            }
            return true;
        }
        return false;
    }

    @Override
    public Item getSeed(){
        return this.seedItem;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random){
        return this.getMetaFromState(state) >= 7 ? random.nextInt(addDropAmount)+minDropAmount : super.quantityDropped(state, fortune, random);
    }

    @Override
    public Item getCrop(){
        return this.returnItem;
    }


    @Override
    public Item getItemDropped(IBlockState state, Random rand, int par3){
        return this.getMetaFromState(state) >= 7 ? this.getCrop() : this.getSeed();
    }


}