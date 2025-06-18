/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class MoCEntityStolenEgg extends Entity {

    public MoCEntityStolenEgg(EntityType<? extends MoCEntityStolenEgg> type, Level world) {
        super(type, world);
    }

    @Override
    protected void defineSynchedData() {
        // Implementation here
    }

    @Override
    protected void readAdditionalSaveData(net.minecraft.nbt.CompoundTag compound) {
        // Implementation here
    }

    @Override
    protected void addAdditionalSaveData(net.minecraft.nbt.CompoundTag compound) {
        // Implementation here
    }
}
