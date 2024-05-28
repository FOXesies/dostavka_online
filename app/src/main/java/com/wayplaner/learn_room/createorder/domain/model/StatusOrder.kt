package com.wayplaner.learn_room.createorder.domain.model

import androidx.compose.ui.graphics.Color
import com.wayplaner.learn_room.ui.theme.cookingStatus
import com.wayplaner.learn_room.ui.theme.cookingStatusBack
import com.wayplaner.learn_room.ui.theme.endCookingStatus
import com.wayplaner.learn_room.ui.theme.endCookingStatusBack
import com.wayplaner.learn_room.ui.theme.endStatus
import com.wayplaner.learn_room.ui.theme.endStatusBack
import com.wayplaner.learn_room.ui.theme.finishStatus
import com.wayplaner.learn_room.ui.theme.finishStatusBack
import com.wayplaner.learn_room.ui.theme.inLineStatus
import com.wayplaner.learn_room.ui.theme.inLineStatusBack
import com.wayplaner.learn_room.ui.theme.onTheWayStatus
import com.wayplaner.learn_room.ui.theme.onTheWayStatusBack
import com.wayplaner.learn_room.ui.theme.waitStatus
import com.wayplaner.learn_room.ui.theme.waitStatusBack

enum class StatusOrder {
    WAIT_ACCEPT,
    IN_LINE_COOKING,
    PROCESS_COOKING,
    COOKING_END,
    ON_TWE_WAY,
    COMPLETE_WAY,
    COMPLETE_ORDER;

    companion object {
        fun StatusOrder.getNext(): StatusOrder? {
            when (this) {
                WAIT_ACCEPT -> return IN_LINE_COOKING
                IN_LINE_COOKING -> return PROCESS_COOKING
                PROCESS_COOKING -> return COOKING_END
                COOKING_END -> return ON_TWE_WAY
                ON_TWE_WAY -> return COMPLETE_WAY
                COMPLETE_WAY -> return COMPLETE_ORDER
                COMPLETE_ORDER -> return null
            }
        }
        fun StatusOrder.getBackColor(): Color {
            when(this){
                WAIT_ACCEPT -> {
                    return waitStatusBack
                }

                IN_LINE_COOKING -> {
                    return inLineStatusBack
                }

                PROCESS_COOKING -> {
                    return cookingStatusBack
                }

                COOKING_END -> {
                    return endCookingStatusBack
                }

                ON_TWE_WAY -> {
                    return onTheWayStatusBack
                }

                COMPLETE_WAY -> {
                    return finishStatusBack
                }

                COMPLETE_ORDER -> {
                    return endStatusBack
                }
            }
        }
        fun StatusOrder.getTextColor(): Color {
            when(this){
                WAIT_ACCEPT -> {
                    return waitStatus
                }

                IN_LINE_COOKING -> {
                    return inLineStatus
                }

                PROCESS_COOKING -> {
                    return cookingStatus
                }

                COOKING_END -> {
                    return endCookingStatus
                }

                ON_TWE_WAY -> {
                    return onTheWayStatus
                }

                COMPLETE_WAY -> {
                    return finishStatus
                }

                COMPLETE_ORDER -> {
                    return endStatus
                }
            }
        }
        fun StatusOrder.getText(): String{
            when(this) {
                WAIT_ACCEPT -> {
                    return "Ожидает подтверждения..."
                }

                IN_LINE_COOKING -> {
                    return "В очереди на готовку"
                }

                PROCESS_COOKING -> {
                    return "Готовится"
                }

                COOKING_END -> {
                    return "Заказ готов"
                }

                ON_TWE_WAY -> {
                    return "Заказ в пути"
                }

                COMPLETE_WAY -> {
                    return "Заказ ждёт вас"
                }

                COMPLETE_ORDER -> {
                    return "Доставлено"
                }
            }
        }
    }
}