/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.world.entity.EntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class MoCEntitySalmon extends MoCEntityMediumFish {

    public MoCEntitySalmon(EntityType<? extends MoCEntitySalmon> type, Level world) {
        super(type, world);
        this.setTypeMoC(1);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("mediumfish_salmon.png");
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        return MoCLootTables.SALMON;
    }
}
