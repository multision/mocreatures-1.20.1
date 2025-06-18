/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.neutral;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.level.BlockEvent;

import java.util.List;

public class MoCEntityEnt extends MoCEntityAnimal {

    private static final Block[] tallgrass = new Block[]{Blocks.GRASS, Blocks.FERN};
    private static final Block[] double_plant = new Block[]{Blocks.SUNFLOWER, Blocks.LILAC, Blocks.ROSE_BUSH, Blocks.PEONY, Blocks.TALL_GRASS, Blocks.LARGE_FERN};
    private static final Block[] red_flower = new Block[]{Blocks.POPPY, Blocks.BLUE_ORCHID, Blocks.ALLIUM, Blocks.AZURE_BLUET, Blocks.RED_TULIP, Blocks.ORANGE_TULIP, Blocks.WHITE_TULIP, Blocks.PINK_TULIP, Blocks.OXEYE_DAISY};
    
    public MoCEntityEnt(EntityType<? extends MoCEntityEnt> type, Level world) {
        super(type, world);
        //setSize(1.4F, 7F);
        setMaxUpStep(2F);
        this.xpReward = 10;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(6, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return MoCEntityAnimal.createAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.ARMOR, 7.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            setTypeMoC(this.random.nextInt(2) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        if (getTypeMoC() == 2) {
            return MoCreatures.proxy.getModelTexture("ent_birch.png");
        }
        return MoCreatures.proxy.getModelTexture("ent_oak.png");
    }

    @Override
    public int getExperienceReward() {
        return 10;
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        if (damagesource.getEntity() != null && damagesource.getEntity() instanceof Player) {
            Player ep = (Player) damagesource.getEntity();
            ItemStack currentItem = ep.getInventory().getSelected();
            Item itemheld = currentItem.getItem();
            if (itemheld instanceof AxeItem) {
                this.level().getDifficulty();
                if (super.shouldAttackPlayers()) {
                    setTarget(ep);
                }
                return super.hurt(damagesource, i);
            }
        }
        // Check if this is fire damage
        if (damagesource == this.damageSources().inFire() || 
            damagesource == this.damageSources().onFire() || 
            damagesource == this.damageSources().lava()) {
            return super.hurt(damagesource, i);
        }
        return false;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        int i = this.random.nextInt(3);
        int qty = this.random.nextInt(12) + 4;
        if (getTypeMoC() == 2) {
            if (i == 0) {
                spawnAtLocation(new ItemStack(Blocks.BIRCH_LOG, qty), 0.0F);
                return;
            }
            if (i == 1) {
                spawnAtLocation(new ItemStack(Items.STICK, qty), 0.0F);
                return;
            }
            spawnAtLocation(new ItemStack(Blocks.BIRCH_SAPLING, qty), 0.0F);
        } else {
            if (i == 0) {
                spawnAtLocation(new ItemStack(Blocks.OAK_LOG, qty), 0.0F);
                return;
            }
            if (i == 1) {
                spawnAtLocation(new ItemStack(Items.STICK, qty), 0.0F);
                return;
            }
            spawnAtLocation(new ItemStack(Blocks.OAK_SAPLING, qty), 0.0F);
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_ENT_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_ENT_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_ENT_AMBIENT.get();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide()) {
            if (this.getTarget() == null && this.random.nextInt(500) == 0) {
                plantOnFertileGround();
            }

            if (this.random.nextInt(100) == 0) {
                attractCritter();
            }
        }
    }

    /**
     * Makes small creatures follow the Ent
     */
    private void attractCritter() {
        List<Entity> list = this.level().getEntities(this, getBoundingBox().inflate(8D, 3D, 8D));
        int n = this.random.nextInt(3) + 1;
        int j = 0;
        for (Entity entity : list) {
            if (entity instanceof Animal && entity.getBbWidth() < 0.6F && entity.getBbHeight() < 0.6F) {
                Animal entityanimal = (Animal) entity;
                if (entityanimal.getTarget() == null && !MoCTools.isTamed(entityanimal)) {
                    entityanimal.setTarget(this);
                    entityanimal.getNavigation().moveTo(this, 1D);
                    j++;
                    if (j > n) {
                        return;
                    }
                }
            }
        }
    }

    private void plantOnFertileGround() {
        BlockPos pos = new BlockPos(Mth.floor(this.getX()), Mth.floor(this.getY()), Mth.floor(this.getZ()));
        Block blockUnderFeet = this.level().getBlockState(pos.below()).getBlock();
        Block blockOnFeet = this.level().getBlockState(pos).getBlock();

        if (Blocks.DIRT.defaultBlockState().is(blockUnderFeet)) {
            Block block = Blocks.GRASS_BLOCK;
            BlockEvent.BreakEvent event = null;
            if (!this.level().isClientSide()) {
                event = new BlockEvent.BreakEvent(this.level(), pos, block.defaultBlockState(), 
                        FakePlayerFactory.get((ServerLevel) this.level(), MoCreatures.MOCFAKEPLAYER));
            }
            if (event != null && !event.isCanceled()) {
                this.level().setBlock(pos.below(), block.defaultBlockState(), 3);
                return;
            }
            return;
        }

        if (Blocks.GRASS_BLOCK.defaultBlockState().is(blockUnderFeet) && blockOnFeet == Blocks.AIR) {
            BlockState iblockstate = getBlockStateToBePlanted();
            int plantChance = 3;
            if (iblockstate.getBlock() instanceof SaplingBlock) {
                plantChance = 10;
            }

            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    BlockPos pos1 = new BlockPos(Mth.floor(this.getX() + x), Mth.floor(this.getY()), Mth.floor(this.getZ() + z));
                    Block blockToPlant = this.level().getBlockState(pos1).getBlock();
                    if (this.random.nextInt(plantChance) == 0 && blockToPlant == Blocks.AIR) {
                        this.level().setBlock(pos1, iblockstate, 3);
                    }
                }
            }
        }
    }

    /**
     * Returns a random blockState
     *
     * @return Any of the flowers, mushrooms, grass and saplings
     */
    private BlockState getBlockStateToBePlanted() {
        Block blockID;
        switch (this.random.nextInt(20)) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return red_flower[this.random.nextInt(red_flower.length)].defaultBlockState();
            case 6:
                return Blocks.BROWN_MUSHROOM.defaultBlockState();
            case 7:
                return Blocks.RED_MUSHROOM.defaultBlockState();
            case 8:
            case 9:
            case 10:
            case 11:
                return tallgrass[this.random.nextInt(tallgrass.length)].defaultBlockState();
            case 12:
            case 13:
            case 14:
            case 15:
                return Blocks.OAK_SAPLING.defaultBlockState();
            case 16:
                return Blocks.BIRCH_SAPLING.defaultBlockState();
            case 17:
                return Blocks.SPRUCE_SAPLING.defaultBlockState();
            case 18:
                return Blocks.JUNGLE_SAPLING.defaultBlockState();
            case 19:
                return Blocks.ACACIA_SAPLING.defaultBlockState();
            default:
                return Blocks.GRASS.defaultBlockState();
        }
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void push(Entity entityIn) {
        if (entityIn.getBbWidth() < 1.5F) {
            super.push(entityIn);
        }
    }

    @Override
    public void doEnchantDamageEffects(LivingEntity entityLivingBaseIn, Entity entityIn) {
        super.doEnchantDamageEffects(entityLivingBaseIn, entityIn);
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    @Override
    protected boolean isImmobile() {
        return false;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return this.getBbHeight() * 0.9F;
    }
}
