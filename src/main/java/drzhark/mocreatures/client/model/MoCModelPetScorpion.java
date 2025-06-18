/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.hunter.MoCEntityPetScorpion;
import net.minecraft.client.model.geom.ModelPart;

public class MoCModelPetScorpion<T extends MoCEntityPetScorpion> extends MoCModelAbstractScorpion<T> {

    public MoCModelPetScorpion(ModelPart root) {
        super(root);
    }

    /**
     * Capture entity state for animation use. In 1.20.1, setLivingAnimations
     * has been replaced by prepareMobModel.
     */
    @Override
    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
        this.poisoning = entity.swingingTail();
        this.isTalking = (entity.mouthCounter != 0);
        this.babies    = entity.getHasBabies();
        this.attacking = entity.armCounter;
        this.sitting   = entity.getIsSitting();
    }
}
