package drzhark.mocreatures.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageUpdatePetName;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

@net.minecraftforge.api.distmarker.OnlyIn(net.minecraftforge.api.distmarker.Dist.CLIENT)
public class MoCGUIEntityNamer extends Screen {

    @SuppressWarnings("removal")
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/gui/pet_naming.png");

    private final IMoCEntity namedEntity;
    private EditBox nameInput;
    private final int imageWidth = 256;
    private final int imageHeight = 181;

    public MoCGUIEntityNamer(IMoCEntity entity, String defaultName) {
        super(Component.literal("Choose your Pet's name:"));
        this.namedEntity = entity;
        this.nameInput = new EditBox(this.font, 0, 0, 200, 20, Component.literal("Pet Name"));
        this.nameInput.setValue(defaultName);
    }

    @Override
    protected void init() {
        super.init();
        int centerX = (this.width - imageWidth) / 2;
        int centerY = (this.height - imageHeight) / 2;

        this.nameInput = new EditBox(this.font, this.width / 2 - 75, centerY + 70, 150, 20, Component.literal("Pet Name"));
        this.nameInput.setMaxLength(30);
        this.nameInput.setFocused(true);
        this.addRenderableWidget(nameInput);

        //this.addRenderableWidget(new Button(this.width / 2 - 75, centerY + 100, 150, 20, Component.literal("Done"), btn -> updateName(), DEFAULT_NARRATION));
        this.addRenderableWidget(
                Button.builder(Component.literal("Done"), btn -> updateName())
                        .bounds(this.width / 2 - 75, centerY + 100, 150, 20)
                        .build()
        );

    }

    private void updateName() {
        String petName = this.nameInput.getValue().trim();
        if (!petName.isEmpty()) {
            this.namedEntity.setPetName(petName);
            if (this.namedEntity instanceof Mob) {
                MoCMessageHandler.INSTANCE.sendToServer(new MoCMessageUpdatePetName(((Mob) this.namedEntity).getId(), petName));
            }
        }
        this.onClose();
    }

    @Override
    public void render(GuiGraphics poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        int centerX = (this.width - imageWidth) / 2;
        int centerY = (this.height - imageHeight) / 2;

        poseStack.blit(TEXTURE, centerX, centerY, 0, 0, imageWidth, imageHeight);

        poseStack.drawCenteredString(this.font, this.title, this.width / 2, centerY + 30, 0xFFFFFF);
        nameInput.render(poseStack, mouseX, mouseY, partialTicks);

        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 257 || keyCode == 335) { // Enter or Numpad Enter
            updateName();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() {
        super.onClose();
        if (this.namedEntity instanceof IMoCTameable tamed) {
            tamed.playTameEffect(true);
        }
    }
}
