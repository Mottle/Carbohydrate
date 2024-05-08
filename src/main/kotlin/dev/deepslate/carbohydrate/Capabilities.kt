package dev.deepslate.carbohydrate

import dev.deepslate.carbohydrate.diet.DietProvider
import dev.deepslate.carbohydrate.diet.IDiet
import dev.deepslate.carbohydrate.nutrition.INutrition
import dev.deepslate.carbohydrate.nutrition.NutritionProvider
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.capabilities.EntityCapability
import net.neoforged.neoforge.capabilities.ItemCapability
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent

@Mod.EventBusSubscriber(modid = Carbohydrate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object Capabilities {
    val DIET: EntityCapability<IDiet, Void> =
        EntityCapability.createVoid(ResourceLocation(Carbohydrate.MOD_ID, "diet"), IDiet::class.java)

    val NUTRITION: ItemCapability<INutrition, Void> =
        ItemCapability.createVoid(ResourceLocation(Carbohydrate.MOD_ID, "nutrition"), INutrition::class.java)

    @SubscribeEvent
    fun register(event: RegisterCapabilitiesEvent) {
        event.registerEntity(DIET, EntityType.PLAYER, DietProvider())
        BuiltInRegistries.ITEM.forEach {
            if(it.isEdible) event.registerItem(NUTRITION, NutritionProvider(), it)
        }
    }
}