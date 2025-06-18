package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.hunter.MoCEntityBigCat;
import drzhark.mocreatures.entity.neutral.MoCEntityWyvern;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.tameable.MoCPetData;
import drzhark.mocreatures.init.MoCEntities;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAppear;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class MoCItemHorseAmulet extends MoCItem {

    private int ageCounter;
    private String name;
    private float health;
    private int age;
    private int creatureType;
    private String spawnClass;
    private boolean isGhost;
    private boolean rideable;
    private byte armor;
    private boolean adult;
    private UUID ownerUniqueId;
    private String ownerName;
    private int PetId;

    public MoCItemHorseAmulet(Item.Properties properties) {
        super(properties.stacksTo(1));
        this.ageCounter = 0;
    }

    @SuppressWarnings("removal")
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        final ItemStack stack = player.getItemInHand(hand);
        if (++this.ageCounter < 2) {
            return InteractionResultHolder.pass(stack);
        }

        if (!level.isClientSide) {
            initAndReadNBT(stack);
        }

        double dist = 3D;
        double newPosY = player.getY();
        double newPosX = player.getX() - (dist * Math.cos((MoCTools.realAngle(player.getYRot() - 90F)) / 57.29578F));
        double newPosZ = player.getZ() - (dist * Math.sin((MoCTools.realAngle(player.getYRot() - 90F)) / 57.29578F));

        if (!level.isClientSide) {
            try {
                MoCEntityTameableAnimal storedCreature;
                this.spawnClass = this.spawnClass.replace(MoCConstants.MOD_PREFIX, "").toLowerCase();

                if (this.spawnClass.equalsIgnoreCase("Wyvern")) {
                    storedCreature = MoCEntities.WYVERN.get().create(level);
                    ((MoCEntityWyvern) storedCreature).setIsGhost(true);
                    this.isGhost = true;
                } else if (this.spawnClass.equalsIgnoreCase("WildHorse")) {
                    storedCreature = MoCEntities.WILDHORSE.get().create(level);
                } else {
                    ResourceLocation res = new ResourceLocation(MoCConstants.MOD_PREFIX + this.spawnClass.toLowerCase());
                    storedCreature = (MoCEntityTameableAnimal) BuiltInRegistries.ENTITY_TYPE.get(res).create(level);
                    if (storedCreature instanceof MoCEntityBigCat) {
                        this.isGhost = true;
                        ((MoCEntityBigCat) storedCreature).setIsGhost(true);
                    }
                }

                storedCreature.setPos(newPosX, newPosY, newPosZ);
                storedCreature.setTypeMoC(this.creatureType);
                storedCreature.setTamed(true);
                storedCreature.setRideable(this.rideable);
                storedCreature.setMoCAge(this.age);
                storedCreature.setPetName(this.name);
                storedCreature.setHealth(this.health);
                storedCreature.setAdult(this.adult);
                storedCreature.setArmorType(this.armor);
                storedCreature.setOwnerPetId(this.PetId);
                storedCreature.setOwnerId(player.getUUID());
                this.ownerName = player.getName().getString();

//                if (this.ownerUniqueId == null) {
//                    this.ownerUniqueId = player.getUUID();
//                    MoCTools.updatePetDataIfAllowed(player, storedCreature);
//                } else {
//                    MoCTools.transferPetOwnershipIfNeeded(this.ownerUniqueId, player, storedCreature);
//                }

                if (level.addFreshEntity(storedCreature)) {
                    MoCMessageHandler.INSTANCE.send(
                            PacketDistributor.NEAR.with(() ->
                                    new PacketDistributor.TargetPoint(player.getX(), player.getY(), player.getZ(), 64, level.dimension())),
                            new MoCMessageAppear(storedCreature.getId())
                    );

                    MoCTools.playCustomSound(storedCreature, MoCSoundEvents.ENTITY_GENERIC_MAGIC_APPEAR.get());

                    // Replace amulet
                    ItemStack replacement = switch (this.creatureType) {
                        case 21, 22 -> new ItemStack(MoCItems.AMULET_GHOST.get());
                        case 26, 27, 28 -> new ItemStack(MoCItems.AMULET_BONE.get());
                        case 39, 40 -> new ItemStack(MoCItems.AMULET_PEGASUS.get());
                        default -> (this.creatureType > 47 && this.creatureType < 60) ? new ItemStack(MoCItems.AMULET_FAIRY.get()) : ItemStack.EMPTY;
                    };
                    if (!replacement.isEmpty()) {
                        player.setItemInHand(hand, replacement);
                    }

                    MoCPetData petData = MoCreatures.instance.mapData.getPetData(storedCreature.getOwnerId());
                    if (petData != null) {
                        petData.setInAmulet(storedCreature.getOwnerPetId(), false);
                    }
                }
            } catch (Exception ex) {
                System.out.println("Unable to find class for entity " + this.spawnClass);
                ex.printStackTrace();
            }
        }

        this.ageCounter = 0;
        return InteractionResultHolder.success(stack);
    }

    public void readFromNBT(CompoundTag tag) {
        this.PetId = tag.getInt("PetId");
        this.creatureType = tag.getInt("CreatureType");
        this.health = tag.getFloat("Health");
        this.age = tag.getInt("Edad");
        this.name = tag.getString("Name");

        int spawnClassOld = tag.getInt("SpawnClass");
        if (spawnClassOld > 0) {
            if (spawnClassOld == 100) {
                this.spawnClass = "Wyvern";
                this.isGhost = true;
            } else {
                this.spawnClass = "WildHorse";
            }
            tag.remove("SpawnClass");
        } else {
            this.spawnClass = tag.getString("SpawnClass");
        }

        this.rideable = tag.getBoolean("Rideable");
        this.armor = tag.getByte("Armor");
        this.adult = tag.getBoolean("Adult");
        this.ownerName = tag.getString("OwnerName");
        if (tag.hasUUID("OwnerUUID")) {
            this.ownerUniqueId = tag.getUUID("OwnerUUID");
        }
    }

    public void writeToNBT(CompoundTag tag) {
        tag.putInt("PetId", this.PetId);
        tag.putInt("CreatureType", this.creatureType);
        tag.putFloat("Health", this.health);
        tag.putInt("Edad", this.age);
        tag.putString("Name", this.name);
        tag.putString("SpawnClass", this.spawnClass);
        tag.putBoolean("Rideable", this.rideable);
        tag.putByte("Armor", this.armor);
        tag.putBoolean("Adult", this.adult);
        tag.putString("OwnerName", this.ownerName);
        if (this.ownerUniqueId != null) {
            tag.putUUID("OwnerUUID", this.ownerUniqueId);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        initAndReadNBT(stack);
        tooltip.add(Component.literal(this.spawnClass).withStyle(ChatFormatting.AQUA));

        if (!this.name.isEmpty()) {
            tooltip.add(Component.literal(this.name).withStyle(ChatFormatting.BLUE));
        }
        if (!this.ownerName.isEmpty()) {
            tooltip.add(Component.literal("Owned by " + this.ownerName).withStyle(ChatFormatting.DARK_BLUE));
        }
    }

    private void initAndReadNBT(ItemStack itemstack) {
        if (!itemstack.hasTag()) {
            itemstack.setTag(new CompoundTag());
        }
        CompoundTag tag = itemstack.getTag();
        readFromNBT(tag);
    }

    }