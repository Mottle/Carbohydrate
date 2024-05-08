package dev.deepslate.carbohydrate

import dev.deepslate.carbohydrate.diet.IDiet
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import kotlin.math.max

object FoodReworkSystem {
    fun doServerPlayerTickLogic(player: ServerPlayer) {
        val time = player.level().gameTime
        if(time % (20 * 60) == 0L) {
            //被动消耗营养
            val diet = player.getCapability(Capabilities.DIET) ?: return
            diet.setCarbohydrate(diet.getCarbohydrate() - 0.1f)
            diet.setGrease(diet.getGrease() - 0.025f)
//            diet.setProtein(diet.getProtein() - 0.08f)
            diet.setPlantFiber(diet.getPlantFiber() - 0.025f)
//            diet.setElectrolyte(diet.getElectrolyte() - 0.08f)
            IDiet.sync(player)
        }

        if(time % 40 == 0L && player.isHurt) {
//            if(player.foodData.foodLevel >= 20) {
//                val heal = max((player.maxHealth - player.health) / 2, 6f)
//                player.heal(heal)
//                player.foodData.foodLevel -= 2
//            } else {
//                player.heal(0.25f)
//            }
            if(player.foodData.foodLevel >= 10) player.heal(0.25f)
        }
    }

    fun doEatenLogic(player: Player, stack: ItemStack): Int {
        val diet = player.getCapability(Capabilities.DIET) ?: return 0
        val extraTicks = diet.getFoodEatingExtraTicks(stack.item)
        if(extraTicks != 0 && player is ServerPlayer) {
            player.sendSystemMessage(Component.literal("你感到难以下咽"))
        }
        return extraTicks
    }
}