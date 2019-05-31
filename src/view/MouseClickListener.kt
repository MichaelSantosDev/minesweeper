package view

import model.Field
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

class MouseClickListener(
        private val field: Field,
        private val onButtonRight: (Field) -> Unit,
        private val onButtonLeft: (Field) -> Unit
): MouseListener{

    override fun mousePressed(e: MouseEvent?) {
        when(e?.button){
            MouseEvent.BUTTON1 -> onButtonRight(field)
            MouseEvent.BUTTON3 -> onButtonLeft(field)
        }
    }

    override fun mouseClicked(e: MouseEvent?) {}

    override fun mouseExited(e: MouseEvent?) {}

    override fun mouseEntered(e: MouseEvent?) {}

    override fun mouseReleased(e: MouseEvent?) {}



}