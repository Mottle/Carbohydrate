package dev.deepslate.carbohydrate.mixin;

import dev.deepslate.carbohydrate.Capabilities;
import dev.deepslate.carbohydrate.Carbohydrate;
import dev.deepslate.carbohydrate.Effects;
import dev.deepslate.carbohydrate.diet.IDiet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public class FoodDataMixin {

    FoodData self() {
        return (FoodData) (Object) this;
    }

    @Inject(method = "eat(Lnet/minecraft/world/item/Item;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)V", at = @At("HEAD"), cancellable = true)
    void handleEaten(Item item, ItemStack stack, LivingEntity entity, CallbackInfo ci) {
        if(!stack.isEdible()) {
            ci.cancel();
            return;
        }
        var prop = stack.getFoodProperties(entity);
        if(prop == null) {
            ci.cancel();
            return;
        }
        if(!(entity instanceof Player)) {
            ci.cancel();
            return;
        }

        if(entity.level().isClientSide) {
            ci.cancel();
            return;
        }
        IDiet diet = entity.getCapability(Capabilities.INSTANCE.getDIET());
        if(diet == null)  {
            ci.cancel();
            return;
        }
//        float rate = diet.getFoodEatenRate(item);
//        self().eat((int)(prop.getNutrition() * rate), prop.getSaturationModifier() * rate);
        diet.eatOver(stack);
        IDiet.Companion.sync((ServerPlayer) entity);
        ci.cancel();
    }
}
