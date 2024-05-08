package dev.deepslate.carbohydrate.diet

import dev.deepslate.carbohydrate.Attachments
import dev.deepslate.carbohydrate.Capabilities
import dev.deepslate.carbohydrate.network.DietSyncPacket
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.network.PacketDistributor
import java.util.*

interface IDiet {
    fun getCarbohydrate(): Float
    fun getGrease(): Float
    fun getProtein(): Float
    fun getPlantFiber(): Float
    fun getElectrolyte(): Float

    fun setCarbohydrate(carbohydrate: Float)
    fun setGrease(grease: Float)
    fun setProtein(protein: Float)
    fun setPlantFiber(plantFiber: Float)
    fun setElectrolyte(electrolyte: Float)

    fun getEatenQueue(): Queue<Item>
    fun updateEatenQueue(eaten: EatenQueue)

    fun getFoodEatingExtraTicks(item: Item): Int
    fun getFoodEatenRate(item: Item): Float
    fun eatOver(stack: ItemStack)
    fun getFoodEatenTimes(item: Item): Int

    companion object {
        fun sync(player: ServerPlayer) {
            val diet = player.getCapability(Capabilities.DIET) ?: return
            with(diet) {
                if (player.level().isClientSide) return
                val data = DietSyncPacket(
                    getCarbohydrate(), getGrease(), getProtein(), getPlantFiber(), getElectrolyte(), player.getData(
                        Attachments.EATEN_QUEUE
                    )
                )
                PacketDistributor.PLAYER.with(player).send(data)
            }
        }
    }
}