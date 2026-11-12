package drzhark.mocreatures.client.model;

import drzhark.mocreatures.client.model.legacy.MoCLegacyModelBigCat1;
import drzhark.mocreatures.client.model.legacy.MoCLegacyModelBigCat2;
import net.minecraft.client.model.geom.ModelPart;

import java.lang.reflect.Field;

/** Model checks that run without launching the game or creating a graphics context. */
public final class LegacyBigCatModelChecks {
    public static void main(String[] args) throws ReflectiveOperationException {
        if (!MoCModelRegistry.LEGACY_BIG_CAT1.equals(MoCLegacyModelBigCat1.LAYER_LOCATION)
                || !MoCModelRegistry.LEGACY_BIG_CAT2.equals(MoCLegacyModelBigCat2.LAYER_LOCATION)) {
            throw new AssertionError("Legacy model layer IDs must match the registered IDs");
        }

        var cat = new MoCLegacyModelBigCat2<>(MoCLegacyModelBigCat2.createBodyLayer().bakeRoot());
        var mane = new MoCLegacyModelBigCat1<>(MoCLegacyModelBigCat1.createBodyLayer().bakeRoot());
        // Reuse the models across a sit/stand transition, as the renderer does.
        for (boolean sitting : new boolean[]{false, true, false}) {
            cat.sitting = sitting;
            cat.setupAnim(null, 1.2F, 0.6F, 3F, 35F, -20F);
            mane.copyPoseFrom(cat);
            for (String name : new String[]{"head", "body"}) {
                ModelPart source = part(cat, name);
                ModelPart target = part(mane, name);
                if (source.x != target.x || source.y != target.y || source.z != target.z
                        || source.xRot != target.xRot || source.yRot != target.yRot || source.zRot != target.zRot) {
                    throw new AssertionError("Mane drifted at " + name + ", sitting=" + sitting);
                }
            }
        }
        System.out.println("Legacy big-cat checks passed: layer IDs, model baking, mane head/body poses, sit/stand reset");
    }

    private static ModelPart part(Object model, String name) throws ReflectiveOperationException {
        Field field = model.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return (ModelPart) field.get(model);
    }
}
