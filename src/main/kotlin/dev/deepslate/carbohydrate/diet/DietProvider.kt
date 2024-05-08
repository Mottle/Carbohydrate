package dev.deepslate.carbohydrate.diet

import net.minecraft.world.entity.player.Player
import net.neoforged.neoforge.capabilities.ICapabilityProvider

class DietProvider : ICapabilityProvider<Player, Void, IDiet> {

    override fun getCapability(player: Player, context: Void?): IDiet = BaseDiet(player)
}