/*
 * This file ("EmpowererRecipe.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.api.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class EmpowererRecipe {

    protected final Ingredient input;
    protected final ItemStack output;

    protected final Ingredient modifier1;
    protected final Ingredient modifier2;
    protected final Ingredient modifier3;
    protected final Ingredient modifier4;

    protected final int energyPerStand;
    protected final float[] particleColor;
    protected final int time;

    @Deprecated
    public EmpowererRecipe(ItemStack input, ItemStack output, ItemStack modifier1, ItemStack modifier2, ItemStack modifier3, ItemStack modifier4, int energyPerStand, int time, float[] particleColor) {
        this(Ingredient.fromStacks(input), output, Ingredient.fromStacks(modifier1), Ingredient.fromStacks(modifier2), Ingredient.fromStacks(modifier3), Ingredient.fromStacks(modifier4), energyPerStand, time, particleColor);
    }

    public EmpowererRecipe(Ingredient input, ItemStack output, Ingredient modifier1, Ingredient modifier2, Ingredient modifier3, Ingredient modifier4, int energyPerStand, int time, float[] particleColor) {
        this.input = input;
        this.output = output;
        this.modifier1 = modifier1;
        this.modifier2 = modifier2;
        this.modifier3 = modifier3;
        this.modifier4 = modifier4;
        this.energyPerStand = energyPerStand;
        this.particleColor = particleColor;
        this.time = time;
    }

    public boolean matches(ItemStack base, ItemStack stand1, ItemStack stand2, ItemStack stand3, ItemStack stand4) {
        return input.apply(base) && modifier1.apply(stand1) && modifier2.apply(stand2) && modifier3.apply(stand3) && modifier4.apply(stand4);
    }

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public Ingredient getStandOne() {
        return modifier1;
    }

    public Ingredient getStandTwo() {
        return modifier2;
    }

    public Ingredient getStandThree() {
        return modifier3;
    }

    public Ingredient getStandFour() {
        return modifier4;
    }

    public int getTime() {
        return time;
    }

    public int getEnergyPerStand() {
        return energyPerStand;
    }

    public float[] getParticleColors() {
        return particleColor;
    }
}
