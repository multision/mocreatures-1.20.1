package drzhark.mocreatures.client.renderer.fx.data;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import drzhark.mocreatures.client.renderer.fx.MoCParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Locale;

public class VanishParticleData implements ParticleOptions {
    public static final ParticleOptions.Deserializer<VanishParticleData> DESERIALIZER = new ParticleOptions.Deserializer<VanishParticleData>() {
        @Override
        public VanishParticleData fromCommand(ParticleType<VanishParticleData> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float r = reader.readFloat();
            reader.expect(' ');
            float g = reader.readFloat();
            reader.expect(' ');
            float b = reader.readFloat();
            reader.expect(' ');
            boolean implode = reader.readBoolean();
            return new VanishParticleData(r, g, b, implode);
        }

        @Override
        public VanishParticleData fromNetwork(ParticleType<VanishParticleData> type, FriendlyByteBuf buffer) {
            return new VanishParticleData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readBoolean());
        }
    };

    public final float red, green, blue;
    public final boolean implode;

    public VanishParticleData(float red, float green, float blue, boolean implode) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.implode = implode;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeFloat(red);
        buffer.writeFloat(green);
        buffer.writeFloat(blue);
        buffer.writeBoolean(implode);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %b", getType(), red, green, blue, implode);
    }

    @Override
    public ParticleType<VanishParticleData> getType() {
        return MoCParticles.VANISH_FX.get();
    }
}
