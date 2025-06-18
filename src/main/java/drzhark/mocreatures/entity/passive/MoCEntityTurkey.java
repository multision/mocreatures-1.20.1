/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIMateMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCEntities;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MoCEntityTurkey extends MoCEntityTameableAnimal {
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.WHEAT_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);

    public MoCEntityTurkey(EntityType<? extends MoCEntityTurkey> type, Level world) {
        super(type, world);
        setAdult(true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
        this.goalSelector.addGoal(2, new EntityAIMateMoC(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(5, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return MoCEntityTameableAnimal.createAttributes()
                .add(Attributes.FOLLOW_RANGE, 12.0D)
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        return MoCEntities.TURKEY.get().create(world);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            setTypeMoC(this.random.nextInt(2) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        if (getTypeMoC() == 1 && !this.isBaby()) {
            return MoCreatures.proxy.getModelTexture("turkey_male.png");
        } else {
            return MoCreatures.proxy.getModelTexture("turkey_female.png");
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_TURKEY_HURT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_TURKEY_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_TURKEY_AMBIENT.get();
    }

    // TODO: Add unique sound event
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        return MoCLootTables.TURKEY;
    }

    @Override
    public boolean compatibleMate(Entity otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (otherAnimal.getClass() != this.getClass()) {
            return false;
        } else if (this.isMale() == ((MoCEntityTurkey) otherAnimal).isMale()) {
            return false;
        } else {
            return this.isInLove() && ((Animal)otherAnimal).isInLove();
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!itemstack.isEmpty()) {
            if (this.isFood(itemstack) && this.getMoCAge() == 0 && this.canFallInLove()) {
                this.usePlayerItem(player, hand, itemstack);
                this.setInLove(player);

                // Extend mating period for Males
                if (this.getTypeMoC() == 1) {
                    this.setInLoveTime(1800);
                }
                return InteractionResult.SUCCESS;
            }

            if (this.isBaby() && this.isFood(itemstack)) {
                this.usePlayerItem(player, hand, itemstack);
                this.ageUp((int) ((float) (-this.getMoCAge() / 20) * 0.1F), true);
                return InteractionResult.SUCCESS;
            }
        }

        final InteractionResult tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getItemInHand(hand);
        if (!getIsTamed() && !stack.isEmpty() && (stack.getItem() == Items.MELON_SEEDS)) {
            if (!this.level().isClientSide) {
                MoCTools.tameWithName(player, this);
            }

            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.onGround() && this.getDeltaMovement().y < 0.0D) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.8D, 1.0D));
        }
    }

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == Items.PUMPKIN_SEEDS;
    }

    @Override
    public int nameYOffset() {
        return -50;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 400;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 2;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 18) {
            for (int i = 0; i < 7; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(ParticleTypes.HEART, 
                    this.getX() + (this.random.nextFloat() * this.getBbWidth() * 2.0F) - this.getBbWidth(),
                    this.getY() + 0.5D + (this.random.nextFloat() * this.getBbHeight()),
                    this.getZ() + (this.random.nextFloat() * this.getBbWidth() * 2.0F) - this.getBbWidth(),
                    d0, d1, d2);
            }
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return this.getBbHeight() * 0.945F;
    }
    
    @Override
    public boolean isMale() {
        return getTypeMoC() == 1;
    }
}
