package view

import model.Board
import model.EventBoard
import javax.swing.*

fun main() {
    MainScreen()
}

class MainScreen(): JFrame(){

    private val board = Board(8, 16, 1)
    private val boardPanel = BoardPanel(board)

    init {
        board.onEvent(this::showResults)
        add(boardPanel)
        setSize(720, 480 )
        setLocationRelativeTo(null)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        title = "Minesweeper in Kotlin by Michael Santos"
        isVisible = true    }

    private fun showResults(event: EventBoard){
        SwingUtilities.invokeLater(){
            val msg = when(event){
                EventBoard.WIN -> "YOU WIN"
                EventBoard.LOSE -> "YOU LOSE"
            }

            JOptionPane.showMessageDialog(this, msg)
            board.restart()
            boardPanel.repaint()
            boardPanel.validate()

        }

    }

}