package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MoCItemRecord extends RecordItem {

    @SuppressWarnings("removal")
    public static final ResourceLocation RECORD_SHUFFLE_RESOURCE = new ResourceLocation(MoCConstants.MOD_ID, "shuffling");

    public MoCItemRecord(int comparatorValueIn, java.util.function.Supplier<SoundEvent> soundSupplier, Item.Properties properties) {
        super(comparatorValueIn, soundSupplier, properties, 2400);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public MutableComponent getDisplayName() {
        return Component.literal("MoC - " + getDescription().getString());
    }
}
