package dev.deepslate.carbohydrate.network

import dev.deepslate.carbohydrate.Carbohydrate
import dev.deepslate.carbohydrate.diet.EatenQueue
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation

data class DietSyncPacket(
    val carbohydrate: Float,
    val grease: Float,
    val protein: Float,
    val plantFiber: Float,
    val electrolyte: Float,
    val queue: EatenQueue
) : CustomPacketPayload {

    companion object {
        val ID = ResourceLocation(Carbohydrate.MOD_ID, "diet_sync_packet")
    }

    constructor(buffer: FriendlyByteBuf) : this(
        buffer.readFloat(),
        buffer.readFloat(),
        buffer.readFloat(),
        buffer.readFloat(),
        buffer.readFloat(),
        EatenQueue().let {
            it.deserializeNBT(buffer.readNbt()!!)
            it
        }
    )

    override fun write(buffer: FriendlyByteBuf) {
        buffer.writeFloat(carbohydrate)
        buffer.writeFloat(grease)
        buffer.writeFloat(protein)
        buffer.writeFloat(plantFiber)
        buffer.writeFloat(electrolyte)
        buffer.writeNbt(queue.serializeNBT())
    }

    override fun id(): ResourceLocation = ID
}