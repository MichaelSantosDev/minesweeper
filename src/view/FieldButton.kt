package view

import model.EventField
import model.Field
import java.awt.Color
import java.awt.Font
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.SwingUtilities
import javax.swing.border.LineBorder

private val COLOR_BG_NORMAL = Color.BLACK
private val COLOR_BG_MARKED = Color.WHITE
private val COLOR_BG_OPENED = Color.DARK_GRAY
private val COLOR_BG_EXPLOSION = Color.RED
private val COLOR_BORDER_BUTTON = Color.DARK_GRAY
private val COLOR_TXT_LOW_DANGER = Color.WHITE
private val COLOR_TXT_MID_DANGER = Color.YELLOW
private val COLOR_TXT_HIGH_DANGER = Color.PINK


class FieldButton (private val field: Field): JButton() {

    init {
        font = font.deriveFont(Font.BOLD)
        background = COLOR_BG_NORMAL
        isOpaque = true
        border = BorderFactory.createBevelBorder(1)
        border = LineBorder(COLOR_BORDER_BUTTON)
        addMouseListener(MouseClickListener(field, {it.open()}, {it.switchMark()}))

        field.onEvent(this::applyButtonStyle)
    }

    private fun applyButtonStyle(field: Field, event: EventField){
        when(event){
            EventField.MINE -> applyButtonStyleExplosion()
            EventField.OPEN -> applyButtonStyleOpen()
            EventField.MARK -> applyButtonStyleMark()
            else -> applyButtonStylePattern()
        }

        SwingUtilities.invokeLater{
            repaint()
            validate()
        }
    }


    private fun applyButtonStyleExplosion(){
        background = COLOR_BG_EXPLOSION
        text = "\uD83D\uDCA3"
    }

    private fun applyButtonStyleOpen(){
        background = COLOR_BG_OPENED
        border = BorderFactory.createLineBorder(COLOR_BG_OPENED)
        foreground = when(field.numOfMinedNeighbors){
            1,2 -> COLOR_TXT_LOW_DANGER
            3,4,5 -> COLOR_TXT_MID_DANGER
            else -> COLOR_TXT_HIGH_DANGER
        }
        val bombs = field.numOfMinedNeighbors
        text = if(bombs > 0) bombs.toString() else ""
    }

    private fun applyButtonStyleMark(){
        background = COLOR_BG_MARKED
        text = "\uD83C\uDFF4"
    }

        private fun applyButtonStylePattern(){
            background = COLOR_BG_NORMAL
            border = BorderFactory.createBevelBorder(0)
            border = LineBorder(COLOR_BORDER_BUTTON)
            text = ""
        }
}