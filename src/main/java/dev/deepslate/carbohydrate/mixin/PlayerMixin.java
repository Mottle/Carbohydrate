package dev.deepslate.carbohydrate.mixin;

import dev.deepslate.carbohydrate.Effects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

    @Unique
    Player self() {
        return (Player) (Object) this;
    }

    @Inject(method = "causeFoodExhaustion", at = @At("HEAD"), cancellable = true)
    void handleCauseFoodExhaustion(float pExhaustion, CallbackInfo ci) {
        if (self().hasEffect(Effects.INSTANCE.getFULL().get())) {
            ci.cancel();
            return;
        }
    }

    @Inject(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;causeFoodExhaustion(F)V", shift = At.Shift.BY, by = 1))
    void handleActuallyHurt(DamageSource pDamageSrc, float pDamageAmount, CallbackInfo ci) {
        //抵消this.causeFoodExhaustion(pDamageSrc.getFoodExhaustion());
        self().causeFoodExhaustion(-pDamageSrc.getFoodExhaustion());
    }
}
