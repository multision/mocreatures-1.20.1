package drzhark.mocreatures.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemHorseGuide extends Item {

    public ItemHorseGuide(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            openClientBookScreen(stack);
        }
        return InteractionResultHolder.success(stack);
    }

    @OnlyIn(Dist.CLIENT)
    private void openClientBookScreen(ItemStack stack) {
        Minecraft.getInstance().setScreen(new BookViewScreen(new BookViewScreen.WrittenBookAccess(stack)));
    }

    // To be called from CreativeModeTabEvent.BuildContents instead of fillItemCategory
    public static void addBookToCreativeTab(NonNullList<ItemStack> items, Item item) {
        ItemStack book = new ItemStack(item);
        CompoundTag tag = new CompoundTag();

        tag.putString("title", "Mo' Creatures Horse Guide");
        tag.putString("author", "forgoted");
        tag.putBoolean("resolved", true);

        CompoundTag display = new CompoundTag();
        display.putString("Name", "{\"text\":\"Mo' Creatures Horse Guide\",\"italic\":false}");

        ListTag lore = new ListTag();
        lore.add(StringTag.valueOf("{\"text\":\"by forgoted\",\"italic\":false,\"color\":\"gray\"}"));
        lore.add(StringTag.valueOf("{\"text\":\"Original\",\"italic\":false,\"color\":\"gray\"}"));
        display.put("Lore", lore);

        tag.put("display", display);

        ListTag pages = new ListTag();
        pages.add(StringTag.valueOf(Serializer.toJson(Component.literal(
                "Welcome to the Mo' Creatures Horse Guide!\n\n" +
                        "Learn detailed methods to breed and raise all horses, special breeds, and mythical creatures."
        ))));

        pages.add(StringTag.valueOf(Serializer.toJson(Component.literal(
                "Tier 1 Horses (Breed Only)\n\n" +
                        "- Vanilla: Natural spawn\n" +
                        "- White/Light Grey: White + Pegasus/Fairy/Unicorn/Nightmare\n" +
                        "- Buckskin: Palomino Snowflake + Bay/Brown"
        ))));

        pages.add(StringTag.valueOf(Serializer.toJson(Component.literal(
                "Tier 1 (continued)\n\n" +
                        "- Blood Bay: Dark Brown/Bay + Buckskin\n" +
                        "- Mahogany/Dark Bay: Grulla Overo + Black Horse\n" +
                        "- Black: Nightmare/Fairy/Unicorn/Pegasus + Black Horse"
        ))));

        pages.add(StringTag.valueOf(Serializer.toJson(Component.literal(
                "Tier 2 Horses\n\n" +
                        "- Palomino Snowflake (wild)\n" +
                        "- Grulla Overo/Grullo (wild)\n" +
                        "- Bay (wild)\n" +
                        "- Dappled Grey: Grey Spotted + Pegasus/Unicorn/Nightmare/Fairy"
        ))));

        pages.add(StringTag.valueOf(Serializer.toJson(Component.literal(
                "Tier 3 Horses\n\n" +
                        "- Bay Tovero: Grulla Overo + Brown Spotted/Blood Bay\n" +
                        "- Palomino Tovero: Grulla Overo + White/Light Grey\n" +
                        "- Grulla/Grullo Tovero: Dappled Grey + White/Light Grey"
        ))));

        pages.add(StringTag.valueOf(Serializer.toJson(Component.literal(
                "Tier 4 Horses (Zebra Taming)\n\n" +
                        "- Black Leopard: Grulla Tovero + Black\n" +
                        "- Black Tovero: Bay Tovero + Black\n\n" +
                        "These horses tame zebras."
        ))));

        pages.add(StringTag.valueOf(Serializer.toJson(Component.literal(
                "Unique Horses\n\n" +
                        "- Zebra (Chest Carrier)\n" +
                        "- Zorse (Sterile)\n" +
                        "- Zonkey (Sterile, Chest Carrier)\n" +
                        "- Mule (Sterile, Chest Carrier)"
        ))));

        pages.add(StringTag.valueOf(Serializer.toJson(Component.literal(
                "Mythical Horses\n\n" +
                        "- Nightmare (No Armor)\n" +
                        "- Unicorn (Armor)\n" +
                        "- Pegasus (Armor)\n" +
                        "- Bat Horse (No Armor)"
        ))));

        pages.add(StringTag.valueOf(Serializer.toJson(Component.literal(
                "Advanced Mythicals\n\n" +
                        "- Dark Pegasus (Chest Carrier)\n" +
                        "- Fairy Horse (Chest Carrier, Armor)"
        ))));

        pages.add(StringTag.valueOf(Serializer.toJson(Component.literal(
                "Undead & Skeleton\n\n" +
                        "- Created: Essence of Undead\n" +
                        "- Decays to Skeleton\n" +
                        "- Heal/Restore: Essence of Light/Undead\n" +
                        "- No Armor"
        ))));

        pages.add(StringTag.valueOf(Serializer.toJson(Component.literal(
                "Breeding & Care Tips\n\n" +
                        "- Enclose horses\n" +
                        "- Feed Bread/Sugar to grow foals\n" +
                        "- Keep chunks loaded\n" +
                        "- Only breed tamed horses"
        ))));

        tag.put("pages", pages);
        book.setTag(tag);
        items.add(book);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
