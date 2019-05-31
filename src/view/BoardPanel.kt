package view

import model.Board
import java.awt.GridLayout
import javax.swing.JPanel

class BoardPanel(board: Board): JPanel(){

    init{
        layout = GridLayout(board.numOfLines, board.numOfColumn)
        board.forEachFields { field ->
            val button = FieldButton(field)
            add(button)
        }
    }


}