package drzhark.mocreatures.util;

import drzhark.mocreatures.init.MoCItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.util.LazyLoadedValue;

import java.util.function.Supplier;

public enum MoCItemTier implements Tier {

    SHARK(1, 161, 7.0F, 2.5F, 15, () -> Ingredient.of(MoCItems.SHARK_TEETH.get())),
    SILVER(2, 304, 9.5F, 3.0F, 19, Ingredient::of),
    SCORPC(2, 371, 7.5F, 2.5F, 16, () -> Ingredient.of(MoCItems.CHITINCAVE.get())),
    SCORPF(2, 371, 7.5F, 2.5F, 16, () -> Ingredient.of(MoCItems.CHITINFROST.get())),
    SCORPN(2, 371, 7.5F, 2.5F, 16, () -> Ingredient.of(MoCItems.CHITINNETHER.get())),
    SCORPD(2, 371, 7.5F, 2.5F, 16, () -> Ingredient.of(MoCItems.CHITIN.get())),
    SCORPU(2, 371, 7.5F, 2.5F, 16, () -> Ingredient.of(MoCItems.CHITINUNDEAD.get())),
    STING(0, 8, 6.0F, 0.0F, 5, Ingredient::of);

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    MoCItemTier(int level, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    @Override public int getUses() { return uses; }
    @Override public float getSpeed() { return speed; }
    @Override public float getAttackDamageBonus() { return damage; }
    @Override public int getLevel() { return level; }
    @Override public int getEnchantmentValue() { return enchantmentValue; }
    @Override public Ingredient getRepairIngredient() { return repairIngredient.get(); }
}
