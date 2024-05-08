package dev.deepslate.carbohydrate

import dev.deepslate.carbohydrate.effect.FullEffect
import net.minecraft.core.registries.Registries
import net.minecraft.world.effect.MobEffect
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

object Effects {
    val EFFECTS: DeferredRegister<MobEffect> = DeferredRegister.create(Registries.MOB_EFFECT, Carbohydrate.MOD_ID)
    val FULL: DeferredHolder<MobEffect, FullEffect> = EFFECTS.register("full", ::FullEffect)
}