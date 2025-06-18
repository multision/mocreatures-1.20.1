/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.event;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.compat.CompatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MoCConstants.MOD_ID)
public class MoCEventHooksClient {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void displayCompatScreen(ScreenEvent.Opening event) {
        if (event.getScreen() instanceof TitleScreen && CompatScreen.showScreen && ModList.get().isLoaded("customspawner")) {
            event.setNewScreen(new CompatScreen());
            CompatScreen.showScreen = false;
        }
    }

    /* TODO: Fix rider rotation
    @SubscribeEvent
    public void renderClimbingRiderPre(RenderLivingEvent.Pre<LivingEntity> event) {
        LivingEntity rider = event.getEntity();
        Entity mount = rider.getVehicle();
        if (mount instanceof MoCEntityScorpion || mount instanceof MoCEntityPetScorpion) {
            if (((LivingEntity) rider.getVehicle()).onClimbable()) {
                Direction facing = rider.getDirection();
                poseStack.pushPose();
                if (facing == Direction.NORTH) {
                    poseStack.mulPose(Axis.X.rotationDegrees(90.0F));
                } else if (facing == Direction.WEST) {
                    poseStack.mulPose(Axis.Z.rotationDegrees(-90.0F));
                } else if (facing == Direction.SOUTH) {
                    poseStack.mulPose(Axis.X.rotationDegrees(-90.0F));
                } else if (facing == Direction.EAST) {
                    poseStack.mulPose(Axis.Z.rotationDegrees(90.0F));
                }
                poseStack.translate(0.0F, -1.0F, 0.0F);
            }
        }
    }

    @SubscribeEvent
    public void renderClimbingRiderPost(RenderLivingEvent.Post<LivingEntity> event) {
        LivingEntity rider = event.getEntity();
        Entity mount = rider.getVehicle();
        if (mount instanceof MoCEntityScorpion || mount instanceof MoCEntityPetScorpion) {
            if (((LivingEntity) rider.getVehicle()).onClimbable()) {
                Direction facing = rider.getDirection();
                poseStack.translate(0.0F, 1.0F, 0.0F);
                if (facing == Direction.NORTH) {
                    poseStack.mulPose(Axis.X.rotationDegrees(-90.0F));
                } else if (facing == Direction.WEST) {
                    poseStack.mulPose(Axis.Z.rotationDegrees(90.0F));
                } else if (facing == Direction.SOUTH) {
                    poseStack.mulPose(Axis.X.rotationDegrees(90.0F));
                } else if (facing == Direction.EAST) {
                    poseStack.mulPose(Axis.Z.rotationDegrees(-90.0F));
                }
                poseStack.popPose();
            }
        }
    }
    */
}
