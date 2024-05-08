package dev.deepslate.carbohydrate

import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(Carbohydrate.MOD_ID)
class Carbohydrate(bus: IEventBus) {
    companion object {
        const val MOD_ID = "carbohydrate"
        val LOGGER: Logger = LogManager.getLogger(MOD_ID)
    }

    init {
        Attachments.ATTACHMENT_TYPES.register(bus)
        Effects.EFFECTS.register(bus)
    }
}