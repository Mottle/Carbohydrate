package dev.deepslate.carbohydrate.client.screen

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.ImageButton
import net.minecraft.client.gui.components.WidgetSprites
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import javax.annotation.Nonnull


class DynamicButton(
    private val containerScreen: AbstractContainerScreen<*>, xIn: Int, yIn: Int, widthIn: Int,
    heightIn: Int, sprites: WidgetSprites, onPressIn: OnPress
) : ImageButton(xIn, yIn, widthIn, heightIn, sprites, onPressIn) {

    override fun renderWidget(
        @Nonnull guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int,
        partialTicks: Float
    ) {
        this.x = containerScreen.guiLeft + 126
        this.y = containerScreen.guiTop - 22 + 83

//        if (this.isHoveredOrFocused) {
//            val tooltips: List<Component> = DietTooltip.getEffects()
//
//            if (!tooltips.isEmpty()) {
//                DietClientEvents.tooltip = tooltips
//                DietClientEvents.tooltipX = mouseX
//                DietClientEvents.tooltipY = mouseY
//            }
//        }
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks)
    }
}