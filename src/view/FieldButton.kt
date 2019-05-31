package view

import model.EventField
import model.Field
import java.awt.Color
import java.awt.Font
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.SwingUtilities

private val COLOR_BG_NORMAL = Color(184, 184, 184)
private val COLOR_BG_MARKED = Color(8, 79, 247)
private val COLOR_BG_EXPLOSION = Color(189, 66, 68)
private val COLOR_TXT_GREEN = Color(0, 100, 0)

class FieldButton (private val field: Field): JButton() {

    init {
        font = font.deriveFont(Font.BOLD)
        background = COLOR_BG_NORMAL
        isOpaque = true
        border = BorderFactory.createBevelBorder(0)
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
        text = "X"
    }

    private fun applyButtonStyleOpen(){
        background = COLOR_BG_NORMAL
        border = BorderFactory.createLineBorder(Color.GRAY)
        foreground = when(field.numOfMinedNeighbors){
            1,2 -> COLOR_TXT_GREEN
            3,4 -> Color.BLUE
            5,6 -> Color.RED
            else -> Color.BLACK
        }
        text = if(field.numOfMinedNeighbors > 0) field.numOfMinedNeighbors.toString() else ""
    }

    private fun applyButtonStyleMark(){
        background = COLOR_BG_MARKED
        foreground = Color.BLACK
        text = "M"
    }

        private fun applyButtonStylePattern(){
            background = COLOR_BG_NORMAL
            border = BorderFactory.createBevelBorder(0)
            text = ""
        }
}