package drzhark.mocreatures.gametest;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.block.MoCBlockLeaf;
import drzhark.mocreatures.config.biome.BiomeSpawnConfig;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.init.MoCBlocks;
import drzhark.mocreatures.init.MoCEntities;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

@GameTestHolder("mocreatures")
@PrefixGameTestTemplate(false)
public final class AuditGameTests {
    @GameTest(template = "audit_empty")
    public static void hostileLightDefaultsAndOverrides(GameTestHelper helper) {
        var level = helper.getLevel();
        level.getServer().setDifficulty(Difficulty.NORMAL, true);
        helper.setBlock(2, 1, 2, Blocks.STONE);
        helper.setBlock(3, 2, 2, Blocks.GLOWSTONE);
        BlockPos pos = helper.absolutePos(new BlockPos(2, 2, 2));
        helper.runAfterDelay(5, () -> {
            helper.assertTrue(!MoCEntityMob.checkMobSpawnRules(MoCEntities.RAT.get(), level,
                    net.minecraft.world.entity.MobSpawnType.NATURAL, pos, level.random), "Rat spawned beside glowstone with default lighting");
            var data = BiomeSpawnConfig.getSpawnData("rat");
            int oldMax = data.maxLightLevel;
            try {
                data.maxLightLevel = 15;
                helper.assertTrue(MoCEntityMob.checkMobSpawnRules(MoCEntities.RAT.get(), level,
                        net.minecraft.world.entity.MobSpawnType.NATURAL, pos, level.random), "Explicit light override was ignored");
                level.getServer().setDifficulty(Difficulty.PEACEFUL, true);
                helper.assertTrue(!MoCEntityMob.checkMobSpawnRules(MoCEntities.RAT.get(), level,
                        net.minecraft.world.entity.MobSpawnType.NATURAL, pos, level.random), "Override allowed a hostile in peaceful");
            } finally {
                data.maxLightLevel = oldMax;
                level.getServer().setDifficulty(Difficulty.NORMAL, true);
            }
            helper.succeed();
        });
    }

    @GameTest(template = "audit_empty")
    public static void rawAndBurningLoot(GameTestHelper helper) {
        var level = helper.getLevel();
        var source = MoCEntities.BOAR.get().create(level);
        String[] paths = {"boar", "bunny", "aquatic/bass", "aquatic/cod", "aquatic/crab", "aquatic/dolphin", "aquatic/piranha", "aquatic/salmon", "polar_bear"};
        var raw = java.util.Set.of(Items.PORKCHOP, Items.RABBIT, Items.COD, Items.SALMON, MoCItems.CRAB_RAW.get());
        var cooked = java.util.Set.of(Items.COOKED_PORKCHOP, Items.COOKED_RABBIT, Items.COOKED_COD, Items.COOKED_SALMON, MoCItems.CRAB_COOKED.get());
        for (String path : paths) {
            var table = level.getServer().getLootData().getLootTable(new ResourceLocation("mocreatures", "entities/" + path));
            for (boolean burning : new boolean[]{false, true}) {
                if (burning) source.setSecondsOnFire(20); else source.clearFire();
                var params = new LootParams.Builder(level)
                        .withParameter(LootContextParams.THIS_ENTITY, source)
                        .withParameter(LootContextParams.ORIGIN, source.position())
                        .withParameter(LootContextParams.DAMAGE_SOURCE, level.damageSources().generic())
                        .create(LootContextParamSets.ENTITY);
                int foodCount = 0;
                for (int i = 0; i < 128; i++) {
                    var drops = table.getRandomItems(params);
                    var expected = burning ? cooked : raw;
                    var forbidden = burning ? raw : cooked;
                    foodCount += drops.stream().filter(stack -> expected.contains(stack.getItem())).mapToInt(ItemStack::getCount).sum();
                    helper.assertTrue(drops.stream().noneMatch(stack -> forbidden.contains(stack.getItem())), "Wrong fire-conditioned food from " + path + ", burning=" + burning);
                }
                helper.assertTrue(foodCount > 0, "No expected food dropped from " + path + ", burning=" + burning);
            }
        }
        helper.succeed();
    }

    @GameTest(template = "audit_empty")
    public static void craftedOstrichSaddleSurvivesReload(GameTestHelper helper) {
        Player player = helper.makeMockSurvivalPlayer();
        var bird = MoCEntities.OSTRICH.get().create(helper.getLevel());
        bird.setTamed(true); bird.setOwnerId(player.getUUID()); bird.setTypeMoC(2); bird.setAdult(true);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(MoCItems.HORSE_SADDLE.get(), 2));
        bird.mobInteract(player, InteractionHand.MAIN_HAND);
        helper.assertTrue(bird.getIsRideable(), "Crafted saddle rejected");
        helper.assertTrue(player.getMainHandItem().getCount() == 1, "Saddle consumed incorrectly");
        CompoundTag tag = new CompoundTag(); bird.addAdditionalSaveData(tag);
        var restored = MoCEntities.OSTRICH.get().create(helper.getLevel()); restored.readAdditionalSaveData(tag);
        helper.assertTrue(restored.getIsRideable(), "Saddle state lost on reload");
        helper.succeed();
    }

    @GameTest(template = "audit_empty")
    public static void whiteLionKeepsManeAfterWings(GameTestHelper helper) {
        Player player = helper.makeMockSurvivalPlayer();
        var lion = MoCEntities.LION.get().create(helper.getLevel());
        lion.setTamed(true); lion.setOwnerId(player.getUUID()); lion.setMoCAge(lion.getMoCMaxAge()); lion.setTypeMoC(7);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(MoCItems.ESSENCE_LIGHT.get()));
        lion.mobInteract(player, InteractionHand.MAIN_HAND);
        helper.assertTrue(lion.getTypeMoC() == 8 && lion.hasMane(), "Winged white male lost mane");
        lion.setAdult(false); lion.setMoCAge(20);
        helper.assertTrue(!lion.hasMane(), "Cub grew an adult mane");
        helper.succeed();
    }

    @GameTest(template = "audit_empty")
    public static void naturalLeavesDecayAndPlacedLeavesStay(GameTestHelper helper) {
        var level = helper.getLevel();
        var block = (MoCBlockLeaf) MoCBlocks.wyvwoodLeaves.get();
        BlockPos leaf = helper.absolutePos(new BlockPos(2, 2, 2));
        BlockPos log = leaf.below();
        level.setBlockAndUpdate(log, MoCBlocks.wyvwoodLog.get().defaultBlockState());
        level.setBlockAndUpdate(leaf, block.defaultBlockState());
        block.tick(level.getBlockState(leaf), level, leaf, level.random);
        helper.assertTrue(!level.getBlockState(leaf).getValue(LeavesBlock.PERSISTENT), "Natural leaf became persistent");
        level.removeBlock(log, false);
        block.tick(level.getBlockState(leaf), level, leaf, level.random);
        block.randomTick(level.getBlockState(leaf), level, leaf, level.random);
        helper.assertTrue(level.getBlockState(leaf).isAir(), "Detached natural leaf did not decay");
        level.setBlockAndUpdate(leaf, block.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true));
        block.tick(level.getBlockState(leaf), level, leaf, level.random);
        block.randomTick(level.getBlockState(leaf), level, leaf, level.random);
        helper.assertTrue(level.getBlockState(leaf).is(block), "Placed leaf decayed");
        helper.succeed();
    }

    @GameTest(template = "audit_empty")
    public static void hatchlingAgesAndTurtleGrowth(GameTestHelper helper) {
        var wyvern = MoCEntities.WYVERN.get().create(helper.getLevel());
        MoCEntityEgg.initializeHatchling(wyvern, 30);
        helper.assertTrue(!wyvern.getIsAdult() && wyvern.getMoCAge() == 30 && wyvern.isBaby(), "Wyvern age systems disagree");
        var manticore = MoCEntities.MANTICORE_PET.get().create(helper.getLevel());
        MoCEntityEgg.initializeHatchling(manticore, 30);
        helper.assertTrue(!manticore.getIsAdult() && manticore.getMoCAge() == 30 && manticore.isBaby(), "Manticore age systems disagree");
        var turtle = MoCEntities.TURTLE.get().create(helper.getLevel());
        helper.assertTrue(!turtle.getIsAdult(), "Young turtle starts adult and cannot grow");
        turtle.setMoCAge(turtle.getMoCMaxAge());
        helper.assertTrue(turtle.getIsAdult(), "Turtle failed to mature");
        helper.succeed();
    }

    @GameTest(template = "audit_empty")
    public static void golemKillBypassesAllPhases(GameTestHelper helper) {
        for (int state = 1; state <= 4; state++) {
            var golem = MoCEntities.BIG_GOLEM.get().create(helper.getLevel());
            golem.setGolemState(state);
            golem.kill();
            helper.assertTrue(golem.isDeadOrDying(), "Kill failed in golem phase " + state);
        }
        helper.succeed();
    }

    @GameTest(template = "audit_empty")
    public static void emptyKittyBedPickupWithHeldItem(GameTestHelper helper) {
        Player player = helper.makeMockSurvivalPlayer();
        player.setShiftKeyDown(true);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.STICK));
        var bed = MoCEntities.KITTY_BED.get().create(helper.getLevel());
        bed.setSheetColor(0);
        bed.mobInteract(player, InteractionHand.MAIN_HAND);
        helper.assertTrue(bed.isRemoved(), "Empty bed swallowed pickup interaction");
        helper.assertTrue(player.getInventory().contains(new ItemStack(MoCItems.KITTYBED[0].get())), "Picked-up bed was lost");
        helper.succeed();
    }
    @GameTest(template = "audit_empty")
    public static void petsPersistButAllowChunkUnload(GameTestHelper helper) {
        for (var type : java.util.List.of(MoCEntities.BUNNY.get(), MoCEntities.DOLPHIN.get(), MoCEntities.BIRD.get(), MoCEntities.KITTY.get())) {
            var pet = (net.minecraft.world.entity.Mob) type.create(helper.getLevel());
            ((drzhark.mocreatures.entity.tameable.IMoCTameable) pet).setTamed(true);
            if (pet instanceof drzhark.mocreatures.entity.neutral.MoCEntityKitty kitty) kitty.setKittyState(3);
            helper.assertTrue(pet.requiresCustomPersistence(), "Pet is eligible for natural despawn: " + type);
            pet.remove(net.minecraft.world.entity.Entity.RemovalReason.UNLOADED_TO_CHUNK);
            helper.assertTrue(pet.isRemoved(), "Pet refused normal chunk unloading: " + type);
        }
        helper.succeed();
    }

    @GameTest(template = "audit_empty")
    public static void fairyParentDropsEquipmentOnce(GameTestHelper helper) {
        var horse = MoCEntities.WILDHORSE.get().create(helper.getLevel());
        horse.setPos(helper.absolutePos(new BlockPos(2, 2, 2)).getCenter());
        horse.setRideable(true); horse.setArmorType(3); horse.setTypeMoC(39); horse.setIsChested(true);
        horse.localChest = new drzhark.mocreatures.entity.inventory.MoCAnimalChest("HorseChest", horse.getInventorySize());
        horse.localChest.setItem(0, new ItemStack(Items.DIAMOND, 7));
        horse.disappearAfterBreeding();
        horse.disappearAfterBreeding();
        var drops = helper.getLevel().getEntitiesOfClass(net.minecraft.world.entity.item.ItemEntity.class, horse.getBoundingBox().inflate(2));
        int saddles = drops.stream().filter(e -> e.getItem().is(MoCItems.HORSE_SADDLE.get())).mapToInt(e -> e.getItem().getCount()).sum();
        int armor = drops.stream().filter(e -> e.getItem().is(Items.DIAMOND_HORSE_ARMOR)).mapToInt(e -> e.getItem().getCount()).sum();
        int diamonds = drops.stream().filter(e -> e.getItem().is(Items.DIAMOND)).mapToInt(e -> e.getItem().getCount()).sum();
        int chests = drops.stream().filter(e -> e.getItem().is(Items.CHEST)).mapToInt(e -> e.getItem().getCount()).sum();
        helper.assertTrue(horse.isRemoved() && saddles == 1 && armor == 1 && diamonds == 7 && chests == 1, "Fairy parent lost or duplicated gear");
        helper.succeed();
    }

    @GameTest(template = "audit_empty")
    public static void dolphinRiderCanAscendAndDive(GameTestHelper helper) {
        for (int x = 1; x <= 5; x++) for (int y = 1; y <= 4; y++) for (int z = 1; z <= 5; z++)
            helper.setBlock(x, y, z, Blocks.WATER);
        var dolphin = MoCEntities.DOLPHIN.get().create(helper.getLevel());
        dolphin.setPos(helper.absolutePos(new BlockPos(3, 2, 3)).getCenter());
        dolphin.setTamed(true);
        helper.getLevel().addFreshEntity(dolphin);
        Player player = helper.makeMockSurvivalPlayer();
        player.startRiding(dolphin, true);
        helper.runAfterDelay(2, () -> {
            helper.assertTrue(dolphin.getControllingPassenger() == player, "Rider was not controlling dolphin");
            helper.assertTrue(dolphin.isInWater(), "Dolphin fixture is outside water");
            dolphin.setDeltaMovement(net.minecraft.world.phys.Vec3.ZERO);
            double before = dolphin.getY();
            dolphin.makeEntityJump();
            dolphin.travel(net.minecraft.world.phys.Vec3.ZERO);
            helper.assertTrue(dolphin.getY() > before, "Ascent input produced no upward movement");
            dolphin.setDeltaMovement(net.minecraft.world.phys.Vec3.ZERO);
            before = dolphin.getY();
            dolphin.makeEntityDive();
            dolphin.travel(net.minecraft.world.phys.Vec3.ZERO);
            helper.assertTrue(dolphin.getY() < before, "Dive input produced no downward movement");
            player.stopRiding();
            dolphin.discard();
            helper.succeed();
        });
    }

    @GameTest(template = "audit_empty")
    public static void scorpionArmorRefreshKeepsHealth(GameTestHelper helper) {
        Player player = helper.makeMockSurvivalPlayer();
        var oldSetting = MoCreatures.proxy.armorSetEffects;
        try {
            MoCreatures.proxy.armorSetEffects = true;
            player.setItemSlot(net.minecraft.world.entity.EquipmentSlot.HEAD, new ItemStack(MoCItems.HELMET_SCORP_D.get()));
            player.setItemSlot(net.minecraft.world.entity.EquipmentSlot.CHEST, new ItemStack(MoCItems.PLATE_SCORP_D.get()));
            player.setItemSlot(net.minecraft.world.entity.EquipmentSlot.LEGS, new ItemStack(MoCItems.LEGS_SCORP_D.get()));
            var boots = new ItemStack(MoCItems.BOOTS_SCORP_D.get());
            player.setItemSlot(net.minecraft.world.entity.EquipmentSlot.FEET, boots);
            var armor = (drzhark.mocreatures.item.MoCItemArmor) boots.getItem();
            player.tickCount = 40;
            armor.onArmorTick(boots, helper.getLevel(), player);
            helper.assertTrue(player.getMaxHealth() == 28F, "Dirt armor bonus missing");
            player.setHealth(28F);
            for (int tick = 80; tick <= 400; tick += 40) {
                player.tickCount = tick;
                armor.onArmorTick(boots, helper.getLevel(), player);
                helper.assertTrue(player.getMaxHealth() == 28F && player.getHealth() == 28F, "Armor refresh reset health");
            }
        } finally {
            MoCreatures.proxy.armorSetEffects = oldSetting;
        }
        helper.succeed();
    }

    @GameTest(template = "audit_empty")
    public static void silkTouchAndCookingRecipes(GameTestHelper helper) {
        var level = helper.getLevel();
        var pos = helper.absolutePos(new BlockPos(2, 2, 2));
        var pick = new ItemStack(Items.DIAMOND_PICKAXE);
        var nest = MoCBlocks.wyvernNestBlock.get().defaultBlockState();
        var normalDrops = net.minecraft.world.level.block.Block.getDrops(nest, level, pos, null, null, pick);
        helper.assertTrue(normalDrops.size() == 1 && normalDrops.get(0).is(MoCBlocks.cobbledWyvstone.get().asItem()), "Normal nest drop changed");
        pick.enchant(net.minecraft.world.item.enchantment.Enchantments.SILK_TOUCH, 1);
        var silkDrops = net.minecraft.world.level.block.Block.getDrops(nest, level, pos, null, null, pick);
        helper.assertTrue(silkDrops.size() == 1 && silkDrops.get(0).is(MoCBlocks.wyvernNestBlock.get().asItem()), "Silk touch lost nest");
        var leaves = MoCBlocks.wyvwoodLeaves.get().defaultBlockState();
        var shearDrops = net.minecraft.world.level.block.Block.getDrops(leaves, level, pos, null, null, new ItemStack(Items.SHEARS));
        helper.assertTrue(shearDrops.size() == 1 && shearDrops.get(0).is(MoCBlocks.wyvwoodLeaves.get().asItem()), "Shears lost wyvern leaves");
        var input = new net.minecraft.world.SimpleContainer(new ItemStack(MoCItems.VENISON_RAW.get()));
        for (var type : java.util.List.of(net.minecraft.world.item.crafting.RecipeType.SMOKING, net.minecraft.world.item.crafting.RecipeType.CAMPFIRE_COOKING)) {
            var recipe = level.getRecipeManager().getRecipeFor(type, input, level);
            helper.assertTrue(recipe.isPresent() && recipe.get().getResultItem(level.registryAccess()).is(MoCItems.VENISON_COOKED.get()), "Venison cooking recipe missing for " + type);
        }
        helper.succeed();
    }

    @GameTest(template = "audit_empty", timeoutTicks = 100)
    public static void wyvernUsesMeleeGoal(GameTestHelper helper) {
        for (int x = 1; x <= 6; x++) for (int z = 1; z <= 6; z++) helper.setBlock(x, 1, z, Blocks.STONE);
        var wyvern = MoCEntities.WYVERN.get().create(helper.getLevel());
        var target = net.minecraft.world.entity.EntityType.IRON_GOLEM.create(helper.getLevel());
        wyvern.setPos(helper.absolutePos(new BlockPos(3, 2, 3)).getCenter());
        target.setPos(wyvern.getX() + 1, wyvern.getY(), wyvern.getZ());
        target.setNoAi(true);
        wyvern.setTamed(true); wyvern.setAdult(true); wyvern.setMoCAge(wyvern.getMoCMaxAge());
        helper.getLevel().addFreshEntity(wyvern);
        helper.getLevel().addFreshEntity(target);
        wyvern.setTarget(target);
        helper.succeedWhen(() -> {
            helper.assertTrue(target.getHealth() < target.getMaxHealth(), "Wyvern never landed melee attack");
            wyvern.discard(); target.discard();
        });
    }

    @GameTest(template = "audit_empty")
    public static void manticoreCannotAttackOwner(GameTestHelper helper) {
        Player owner = helper.makeMockSurvivalPlayer();
        var pet = MoCEntities.MANTICORE_PET.get().create(helper.getLevel());
        pet.setTamed(true); pet.setOwnerId(owner.getUUID());
        helper.assertTrue(!pet.canAttack(owner), "Tamed manticore can select its owner as a target");
        float health = owner.getHealth();
        helper.assertTrue(!pet.doHurtTarget(owner) && owner.getHealth() == health, "Tamed manticore hurt its owner");
        helper.succeed();
    }

    @GameTest(template = "audit_empty")
    public static void ogreIgnoresCreativeRetaliation(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();
        player.getAbilities().instabuild = true;
        var ogre = MoCEntities.OGRE.get().create(helper.getLevel());
        ogre.hurt(helper.getLevel().damageSources().playerAttack(player), 1F);
        helper.assertTrue(ogre.getTarget() == null, "Ogre selected creative attacker for retaliation");
        helper.succeed();
    }

}
