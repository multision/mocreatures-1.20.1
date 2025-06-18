package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.aquatic.*;
import drzhark.mocreatures.entity.hunter.MoCEntityKomodo;
import drzhark.mocreatures.entity.hunter.MoCEntityManticorePet;
import drzhark.mocreatures.entity.hunter.MoCEntityPetScorpion;
import drzhark.mocreatures.entity.hunter.MoCEntitySnake;
import drzhark.mocreatures.entity.neutral.MoCEntityOstrich;
import drzhark.mocreatures.entity.neutral.MoCEntityWyvern;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import drzhark.mocreatures.init.MoCEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class MoCEntityEgg extends Mob {

    public int eggType;
    private int tCounter;
    private int lCounter;

    public MoCEntityEgg(EntityType<? extends Mob> type, Level level) {
        super(type, level);
        this.setNoAi(true);
        this.setSilent(true);
        this.tCounter = 0;
        this.lCounter = 0;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D);
    }

    @Override
    protected void registerGoals() {}

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("egg.png");
    }

    @Override
    public void aiStep() {
        this.setDeltaMovement(0, this.getDeltaMovement().y, 0);
        this.yHeadRot = this.getYRot();
        this.yBodyRot = this.getYRot();

        if (!this.level().isClientSide) {
            if (this.random.nextInt(20) == 0) this.lCounter++;
            if (this.lCounter > 500 && this.level().getNearestPlayer(this, 24D) == null) this.discard();

            boolean inWater = this.isInWater();
            if (inWater && (getEggType() < 12 || getEggType() > 69) && (this.random.nextInt(20) == 0)) handleAquaticHatching();
            else if (!inWater && getEggType() > 20 && (this.random.nextInt(20) == 0)) handleLandHatching();
        }
    }

    private void handleAquaticHatching() {
        this.tCounter++;
        if (this.tCounter % 5 == 0) this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.2D, 0.0D));
        if (this.tCounter == 5) notifyEggHatching();

        if (this.tCounter >= 30) {
            Player player = this.level().getNearestPlayer(this, 24D);
            switch (getEggType()) {
                case 11:
                    spawnEggEntity(new MoCEntityShark(MoCEntities.SHARK.get(), this.level()), player); break;
                case 90:
                    spawnEggEntity(new MoCEntityPiranha(MoCEntities.PIRANHA.get(), this.level()), player); break;
                default:
                    if (getEggType() <= 10) {
                        MoCEntityFishy fishy = new MoCEntityFishy(MoCEntities.FISHY.get(), this.level());
                        fishy.setTypeMoC(getEggType());
                        spawnEggEntity(fishy, player);
                    } else if (getEggType() >= 80) {
                        spawnEggEntity(MoCEntitySmallFish.createEntity(this.level(), getEggType() - 79), player);
                    } else if (getEggType() >= 70) {
                        spawnEggEntity(MoCEntityMediumFish.createEntity(this.level(), getEggType() - 69), player);
                    }
            }
            MoCTools.playCustomSound(this, SoundEvents.CHICKEN_EGG);
            this.discard();
        }
    }

    public int getSize() {
        if (getEggType() == 30 || getEggType() == 31) {
            return 170;
        }
        return 100;
    }

    private void handleLandHatching() {
        this.tCounter++;
        if (this.tCounter % 5 == 0) this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.2D, 0.0D));
        if (this.tCounter == 5) notifyEggHatching();

        if (this.tCounter >= 30) {
            Player player = this.level().getNearestPlayer(this, 24D);

            if (getEggType() > 20 && getEggType() < 29) {
                MoCEntitySnake snakey = new MoCEntitySnake(MoCEntities.SNAKE.get(), this.level());
                snakey.setTypeMoC(getEggType() - 20);
                spawnEggEntity(snakey, player);
            }

            if (getEggType() == 30 || getEggType() == 31 || getEggType() == 32) {
                MoCEntityOstrich ostrich = new MoCEntityOstrich(MoCEntities.OSTRICH.get(), this.level());
                ostrich.setTypeMoC(this.level().dimension().location().getPath().contains("nether") || getEggType() == 32 ? 5 : 1);
                ostrich.setAge(35);
                ostrich.setPos(this.getX(), this.getY(), this.getZ());
                this.level().addFreshEntity(ostrich);
                if (getEggType() == 31 && player != null) MoCTools.tameWithName(player, ostrich);
            }

            if (getEggType() == 33)
                spawnEggEntity(new MoCEntityKomodo(MoCEntities.KOMODO_DRAGON.get(), this.level()), player);

            if (getEggType() > 40 && getEggType() < 46) {
                MoCEntityPetScorpion scorp = new MoCEntityPetScorpion(MoCEntities.PET_SCORPION.get(), this.level());
                scorp.setTypeMoC(getEggType() - 40); scorp.setAdult(false);
                scorp.setPos(this.getX(), this.getY(), this.getZ());
                this.level().addFreshEntity(scorp);
                if (player != null) MoCTools.tameWithName(player, scorp);
            }

            if (getEggType() > 49 && getEggType() < 62) {
                MoCEntityWyvern wyv = new MoCEntityWyvern(MoCEntities.WYVERN.get(), this.level());
                wyv.setTypeMoC(getEggType() - 49); wyv.setAdult(false); wyv.setAge(30);
                spawnEggEntity(wyv, player); wyv.setHealth(wyv.getMaxHealth());
            }

            if (getEggType() > 61 && getEggType() < 67) {
                MoCEntityManticorePet mant = new MoCEntityManticorePet(MoCEntities.MANTICORE_PET.get(), this.level());
                mant.setTypeMoC(getEggType() - 61); mant.setAdult(false); mant.setAge(30);
                spawnEggEntity(mant, player); mant.setHealth(mant.getMaxHealth());
            }

            MoCTools.playCustomSound(this, SoundEvents.CHICKEN_EGG);
            this.discard();
        }
    }

    private void spawnEggEntity(Mob entity, Player owner) {
        entity.setPos(this.getX(), this.getY(), this.getZ());
        if (entity instanceof AgeableMob) ((AgeableMob) entity).setAge(-24000);
        this.level().addFreshEntity(entity);
        if (owner != null) MoCTools.tameWithName(owner, (IMoCTameable) entity);
    }

    private void notifyEggHatching() {
        Player player = this.level().getNearestPlayer(this, 24D);
        if (player != null) {
            player.sendSystemMessage(Component.translatable("msg.mocreatures.egg", (int) this.getX(), (int) this.getY(), (int) this.getZ()));
        }
    }

    public int getEggType() { return this.eggType; }
    public void setEggType(int eggType) { this.eggType = eggType; }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.eggType = compound.getInt("EggType");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("EggType", this.eggType);
    }

    // Always render the egg regardless of distance
    @Override
    public boolean shouldRenderAtSqrDistance(double distanceSq) { return true; }

    @Override
    public boolean canBeCollidedWith() { return true; }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
