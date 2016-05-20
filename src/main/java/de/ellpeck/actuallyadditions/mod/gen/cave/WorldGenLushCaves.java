/*
 * This file ("WorldGenLushCaves.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.gen.cave;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WorldGenLushCaves extends WorldGenerator{

    private final Random rand;

    public WorldGenLushCaves(Random rand){
        this.rand = rand;
    }

    @Override
    public boolean generate(@Nonnull World world, @Nonnull Random rand, @Nonnull BlockPos position){
        this.generateCave(world, position);
        return true;
    }

    private void generateCave(World world, BlockPos center){
        int spheres = this.rand.nextInt(5)+3;
        for(int i = 0; i <= spheres; i++){
            this.makeSphereWithGrassFloor(world, center.add(this.rand.nextInt(11)-5, this.rand.nextInt(7)-3, this.rand.nextInt(11)-5), this.rand.nextInt(3)+5);
        }

        this.genTreesAndTallGrass(world, center, 10, spheres*3);
    }

    private void genTreesAndTallGrass(World world, BlockPos center, int radius, int amount){
        List<BlockPos> possiblePoses = new ArrayList<BlockPos>();
        for(double x = -radius; x < radius; x++){
            for(double y = -radius; y < radius; y++){
                for(double z = -radius; z < radius; z++){
                    if(this.rand.nextDouble() >= 0.5D){
                        BlockPos pos = center.add(x, y, z);
                        if(world.getBlockState(pos).getBlock() == Blocks.GRASS){
                            possiblePoses.add(pos);
                        }
                    }
                }
            }
        }

        if(!possiblePoses.isEmpty()){
            for(int i = 0; i <= amount; i++){
                Collections.shuffle(possiblePoses);
                if(this.rand.nextBoolean()){
                    WorldGenAbstractTree trees = this.rand.nextBoolean() ? (this.rand.nextBoolean() ? new WorldGenBigTree(false) : new WorldGenShrub(Blocks.LOG.getDefaultState(), Blocks.LEAVES.getDefaultState())) : new WorldGenTrees(false);
                    trees.generate(world, this.rand, possiblePoses.get(0).up());
                }
                else{
                    ItemDye.applyBonemeal(new ItemStack(Items.DYE, 1, EnumDyeColor.WHITE.getDyeDamage()), world, possiblePoses.get(0));
                }
            }
        }
    }

    private void makeSphereWithGrassFloor(World world, BlockPos center, int radius){
        for(double x = -radius; x < radius; x++){
            for(double y = -radius; y < radius; y++){
                for(double z = -radius; z < radius; z++){
                    if(Math.sqrt((x*x)+(y*y)+(z*z)) < radius){
                        world.setBlockToAir(center.add(x, y, z));
                    }
                }
            }
        }

        for(double x = -radius; x < radius; x++){
            for(double z = -radius; z < radius; z++){
                for(double y = -radius; y <= -3; y++){
                    BlockPos pos = center.add(x, y, z);
                    IBlockState state = world.getBlockState(pos);
                    BlockPos posUp = pos.up();
                    IBlockState stateUp = world.getBlockState(posUp);
                    if(!state.getBlock().isAir(state, world, pos) && stateUp.getBlock().isAir(stateUp, world, posUp)){
                        world.setBlockState(pos, Blocks.GRASS.getDefaultState());
                    }
                }
            }
        }
    }
}
