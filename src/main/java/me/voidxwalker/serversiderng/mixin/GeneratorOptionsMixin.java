package me.voidxwalker.serversiderng.mixin;

import me.voidxwalker.serversiderng.RNGHandler;
import me.voidxwalker.serversiderng.ServerSideRng;
import me.voidxwalker.serversiderng.Speedrun;
import net.minecraft.world.gen.GeneratorOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.OptionalLong;

@Mixin(GeneratorOptions.class)
public class GeneratorOptionsMixin {
    /**
     * Uses the from {@link RNGHandler#getRngValue(RNGHandler.RNGTypes)} obtained random {@code Long}, that has been generated by verification server, for the world seed.
     * <p>
     * Starts a new {@link Speedrun}.
     * @author Void_X_Walker
     * @see ServerSideRng#startSpeedrun()
     */
    @ModifyVariable(method = "withHardcore", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private OptionalLong modifySeedRandom(OptionalLong l) {
        ServerSideRng.startSpeedrun();
        if(ServerSideRng.inSpeedrun()){
            return OptionalLong.of( ServerSideRng.currentSpeedrun.getCurrentRNGHandler().getRngValue(RNGHandler.RNGTypes.WORLD_SEED));
        }
        return l;
    }
}
