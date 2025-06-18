package drzhark.mocreatures.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class MoCItemTurtleSoup extends MoCItemFood {

    protected MoCItemTurtleSoup(MoCItemFood.Builder builder) {
        super(builder);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack resultStack = super.finishUsingItem(stack, level, entity);
        return (entity instanceof Player && ((Player) entity).isCreative()) ? resultStack : new ItemStack(Items.BOWL);
    }

    public static class Builder extends MoCItemFood.Builder {
        public Builder(Item.Properties properties, int nutrition) {
            this(properties, nutrition, 0.6F, false);
        }

        public Builder(Item.Properties properties, int nutrition, float saturation, boolean isWolfFood) {
            this(properties, nutrition, saturation, isWolfFood, 32);
        }

        public Builder(Item.Properties properties, int nutrition, float saturation, boolean isWolfFood, int eatingSpeed) {
            super(properties, nutrition, saturation, isWolfFood, eatingSpeed);
        }

        public MoCItemTurtleSoup build() {
            return new MoCItemTurtleSoup(this);
        }
    }
}
