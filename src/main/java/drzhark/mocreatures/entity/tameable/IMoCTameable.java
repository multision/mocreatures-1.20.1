/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.tameable;

import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;
import java.util.UUID;

public interface IMoCTameable extends IMoCEntity {

    boolean isRiderDisconnecting();

    void setRiderDisconnecting(boolean flag);

    void playTameEffect(boolean par1);

    void setTamed(boolean par1);

    void remove(Entity.RemovalReason reason);

    void addAdditionalSaveData(CompoundTag nbttagcompound);

    void readAdditionalSaveData(CompoundTag nbttagcompound);

    void setOwnerId(@Nullable UUID uuid);

    float getPetHealth();

    void spawnHeart();

    boolean readytoBreed();

    String getOffspringClazz(IMoCTameable mate);

    int getOffspringTypeInt(IMoCTameable mate);

    boolean compatibleMate(Entity mate);

    boolean getHasEaten();

    void setHasEaten(boolean flag);

    int getGestationTime();

    void setGestationTime(int time);
}
