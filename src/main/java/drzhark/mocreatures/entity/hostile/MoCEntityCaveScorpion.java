/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.Random;

public class MoCEntityCaveScorpion extends MoCEntityScorpion {

    public MoCEntityCaveScorpion(EntityType<? extends MoCEntityCaveScorpion> type, Level world) {
        super(type, world, 2);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return MoCEntityScorpion.createAttributes()
                .add(Attributes.FOLLOW_RANGE, 24.0D)
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.325D)
                .add(Attributes.ATTACK_DAMAGE, 3.5D)
                .add(Attributes.ARMOR, 4.0D);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("scorpion_cave.png");
    }

    @Override
    public void doEnchantDamageEffects(LivingEntity attacker, Entity target) {
        if (!getIsPoisoning() && this.random.nextInt(5) == 0 && target instanceof LivingEntity livingTarget) {
            setPoisoning(true);
            livingTarget.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 15 * 20, 0)); // 15 seconds
            livingTarget.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 15 * 20, 0));
        } else {
            swingArm();
        }
        super.doEnchantDamageEffects(attacker, target);
    }

    public static boolean getCanSpawnHere(EntityType<? extends MoCEntityMob> type, ServerLevel world, MobSpawnType reason, BlockPos pos, Random randomIn) {
        return MoCEntityScorpion.getCanSpawnHere(type, world, reason, pos, randomIn) && !world.canSeeSky(pos) && (pos.getY() < 50.0D);
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        return MoCLootTables.CAVE_SCORPION;
    }
}
