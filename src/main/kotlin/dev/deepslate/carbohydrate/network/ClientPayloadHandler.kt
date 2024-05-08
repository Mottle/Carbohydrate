package dev.deepslate.carbohydrate.network

import dev.deepslate.carbohydrate.Capabilities
import net.neoforged.neoforge.network.handling.PlayPayloadContext

object ClientPayloadHandler {
    fun handle(data: DietSyncPacket, context: PlayPayloadContext) {
        context.workHandler.submitAsync {
            context.player.map {
                val diet = it.getCapability(Capabilities.DIET) ?: return@map
                diet.setCarbohydrate(data.carbohydrate)
                diet.setGrease(data.grease)
                diet.setProtein(data.protein)
                diet.setPlantFiber(data.plantFiber)
                diet.setElectrolyte(data.electrolyte)
                diet.updateEatenQueue(data.queue)
            }
        }
    }
}