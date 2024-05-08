package dev.deepslate.carbohydrate.network

import dev.deepslate.carbohydrate.Carbohydrate
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent

@Mod.EventBusSubscriber(modid = Carbohydrate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object Handler {
    @SubscribeEvent
    fun register(event: RegisterPayloadHandlerEvent) {
        val registerer = event.registrar(Carbohydrate.MOD_ID)
        registerer.play(DietSyncPacket.ID, ::DietSyncPacket) { handler ->
            handler.client(ClientPayloadHandler::handle)
        }
    }
}