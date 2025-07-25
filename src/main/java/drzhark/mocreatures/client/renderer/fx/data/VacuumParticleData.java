package drzhark.mocreatures.client.renderer.fx.data;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import drzhark.mocreatures.client.renderer.fx.MoCParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Locale;

public class VacuumParticleData implements ParticleOptions {
    public static final ParticleOptions.Deserializer<VacuumParticleData> DESERIALIZER = new ParticleOptions.Deserializer<VacuumParticleData>() {
        @Override
        public VacuumParticleData fromCommand(ParticleType<VacuumParticleData> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float r = reader.readFloat();
            reader.expect(' ');
            float g = reader.readFloat();
            reader.expect(' ');
            float b = reader.readFloat();
            return new VacuumParticleData(r, g, b);
        }

        @Override
        public VacuumParticleData fromNetwork(ParticleType<VacuumParticleData> type, FriendlyByteBuf buffer) {
            return new VacuumParticleData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
        }
    };

    public final float red, green, blue;

    public VacuumParticleData(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeFloat(red);
        buffer.writeFloat(green);
        buffer.writeFloat(blue);
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f", getType(), red, green, blue);
    }

    @Override
    public ParticleType<VacuumParticleData> getType() {
        return MoCParticles.VACUUM_FX.get();
    }
}
