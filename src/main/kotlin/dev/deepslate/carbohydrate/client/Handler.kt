package dev.deepslate.carbohydrate.client

import com.mojang.datafixers.util.Either
import dev.deepslate.carbohydrate.Capabilities
import dev.deepslate.carbohydrate.Carbohydrate
import dev.deepslate.carbohydrate.client.screen.DynamicButton
import dev.deepslate.carbohydrate.client.screen.HealthScreen
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.WidgetSprites
import net.minecraft.client.gui.screens.inventory.InventoryScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.client.event.RenderTooltipEvent
import net.neoforged.neoforge.client.event.ScreenEvent

@Mod.EventBusSubscriber(modid = Carbohydrate.MOD_ID, value = [Dist.CLIENT])
object Handler {
    @SubscribeEvent
    fun handleInitGui(event: ScreenEvent.Init.Post) {
        val screen = event.screen as? InventoryScreen ?: return
        val on = ResourceLocation(Carbohydrate.MOD_ID, "on")
        val off = ResourceLocation(Carbohydrate.MOD_ID, "off")
        val sprites = WidgetSprites(off, on)
        val button = DynamicButton(screen, screen.guiLeft + 126, screen.height / 2 - 22, 20, 18, sprites) { _ ->
            Minecraft.getInstance().setScreen(HealthScreen(true))
        }
        event.addListener(button)
    }

    @SubscribeEvent
    fun handleTooltips(event: RenderTooltipEvent.GatherComponents) {
        val stack = event.itemStack
        val item = stack.item
        if (!item.isEdible) return
        val player = Minecraft.getInstance().player ?: return
        val diet = player.getCapability(Capabilities.DIET) ?: return
        val eatTimes = diet.getFoodEatenTimes(item)

        val nutrition = stack.getCapability(Capabilities.NUTRITION)
        if (nutrition != null) {
            val foodLevel = nutrition.getFoodLevel()
            val levelText = when (foodLevel) {
                1 -> "小食"
                2 -> "简餐"
                3 -> "盛宴"
                else -> "零嘴"
            }
            event.tooltipElements.add(
                Either.left(
                    Component.literal(levelText).withStyle(ChatFormatting.BOLD, ChatFormatting.GREEN)
                )
            )

            fun addNutrition(name: String, value: Float) {
                if (value <= 0) return
                val formated = "%.1f".format(value)
                event.tooltipElements.add(
                    Either.left(
                        Component.literal("$name: $formated%").withStyle(ChatFormatting.ITALIC, ChatFormatting.BLUE)
                    )
                )
            }

            addNutrition("碳水化合物", nutrition.getCarbohydrate())
            addNutrition("油脂", nutrition.getGrease())
            addNutrition("蛋白质", nutrition.getProtein())
            addNutrition("植物纤维", nutrition.getPlantFiber())
            addNutrition("电解质", nutrition.getElectrolyte())
        }

        val rate = (diet.getFoodEatenRate(item) * 100f).toInt()
        event.tooltipElements.add(
            Either.left(
                Component.literal("食物效果: $rate%").withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_PURPLE)
            )
        )

        if (eatTimes != 0) {
            event.tooltipElements.add(
                Either.left(
                    Component.literal("在过去10次中你食用了${eatTimes}次")
                        .withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_PURPLE)
                )
            )
        } else {
            event.tooltipElements.add(
                Either.left(
                    Component.literal("最近还没吃过呢").withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_PURPLE)
                )
            )
        }
    }
}