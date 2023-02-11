package me.voidxwalker.serversiderng.mixin.rng_verification;

import me.voidxwalker.serversiderng.RNGHandler;
import me.voidxwalker.serversiderng.ServerSideRNG;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;
import java.util.function.Supplier;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    /**
     * Uses the from {@link  ServerSideRNG#getRngContext(RNGHandler.RNGTypes)} obtained random {@code Long}, that has been generated by the {@code Verification-Server}, as a seed for the {@link RNGHandler.RNGTypes#ENCHANTMENT} RNG.
     * @see  ServerSideRNG#getRngContext(RNGHandler.RNGTypes)
     * @author Void_X_Walker
     */
    @Redirect(method = "applyEnchantmentCosts",at = @At(value = "INVOKE",target = "Ljava/util/Random;nextInt()I"))
    public int modifyEnchantmentRandom(Random random){
        Random targetRandom= ServerSideRNG.getRngContext(RNGHandler.RNGTypes.ENCHANTMENT)
                .map(Supplier::get)
                .map(Random::new)
                .orElse(random);
        return targetRandom.nextInt();
    }
}
