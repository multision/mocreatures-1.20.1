package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.tameable.IMoCTameable;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class EntityAITargetNonTamedMoC<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    private final PathfinderMob tameable;

    public EntityAITargetNonTamedMoC(PathfinderMob creature, Class<T> classTarget, boolean checkSight) {
        super(creature, classTarget, checkSight);
        this.tameable = creature;
    }

    public boolean canUse() {
        return this.tameable instanceof IMoCTameable && !((IMoCTameable) this.tameable).getIsTamed() && super.canUse();
    }
}