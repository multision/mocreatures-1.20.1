/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.tameable.IMoCTameable;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EntityAITools {

    protected static boolean IsNearPlayer(Mob entityliving, double d) {
        Player entityplayer1 = entityliving.level().getNearestPlayer(entityliving, d);
        return entityplayer1 != null;
    }

    protected static Player getIMoCTameableOwner(IMoCTameable pet) {
        if (pet.getOwnerId() == null) {
            return null;
        }

        Mob mobEntity = (Mob)pet;
        Level level = mobEntity.level();
        
        for (int i = 0; i < level.players().size(); ++i) {
            Player entityplayer = level.players().get(i);

            if (pet.getOwnerId().equals(entityplayer.getUUID())) {
                return entityplayer;
            }
        }
        return null;
    }
}
