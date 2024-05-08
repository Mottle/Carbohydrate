package dev.deepslate.carbohydrate

import dev.deepslate.carbohydrate.diet.IDiet
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.event.TickEvent.PlayerTickEvent
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerRespawnEvent

@Mod.EventBusSubscriber(modid = Carbohydrate.MOD_ID)
object Handler {
    @SubscribeEvent
    fun handleEaten(event: LivingEntityUseItemEvent.Start) {
        val player = event.entity as? Player ?: return
        val stack = event.item
        if (!stack.isEdible) return
        val extraTicks = FoodReworkSystem.doEatenLogic(player, stack)
        event.duration += extraTicks
    }

    @SubscribeEvent
    fun handleServerTick(event: PlayerTickEvent) {
        val player = event.player as? ServerPlayer ?: return
        FoodReworkSystem.doServerPlayerTickLogic(player)
    }

    @SubscribeEvent
    fun handleLogin(event: PlayerLoggedInEvent) {
        val player = event.entity as? ServerPlayer ?: return
        IDiet.sync(player)
    }

    @SubscribeEvent
    fun handleRespawn(event: PlayerRespawnEvent) {
        val player = event.entity as? ServerPlayer ?: return
        IDiet.sync(player)
    }
}