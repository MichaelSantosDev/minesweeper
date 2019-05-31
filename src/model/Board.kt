package model

import kotlin.random.Random

class Board(val numOfLines: Int, val numOfColumn: Int, val numOfMines: Int){
    private val fields = ArrayList<ArrayList<Field>>()
    private val callbacks = ArrayList<(EventBoard) -> Unit>()

    init {
        generateFields()
        bindNeighbors()
        drawMines()
    }

    private fun generateFields(){
        for(line in 0 until numOfLines){
            fields.add(ArrayList())
            for(column in 0 until numOfColumn){
                val newField = Field(line, column)
                newField.onEvent(this::checkGameStatus)
                fields[line].add(newField)
            }
        }
    }

    private fun bindNeighbors(){
        forEachFields { bindNeighbors(it)  }
    }

    private fun bindNeighbors(field: Field){
        val (line, column) = field
        val lines = arrayOf(line -1, line, line + 1)
        val columns = arrayOf(column -1, column, column + 1)

        lines.forEach { l ->
            columns.forEach { c ->
                val current  = fields.getOrNull(l)?.getOrNull(c)
                current?.takeIf { field != it }?.let { field.addNeighbour(it) }
            }
        }
    }

    private fun drawMines(){
        var drawedLine = -1
        var drawedColumn = -1
        var numOfMines = 0

        while(numOfMines < this.numOfMines){
            drawedLine = Random.nextInt(numOfLines)
            drawedColumn = Random.nextInt(numOfColumn)

            val drawedField = fields[drawedLine][drawedColumn]
            if(drawedField.safe){
                drawedField.mine()
                numOfMines++
            }
        }

    }

    fun goalAchieved(): Boolean{
        var playerWinner = true
        forEachFields { if( !it.goalAchieved ) playerWinner = false}
        return playerWinner
    }

    fun checkGameStatus(field: Field, event: EventField){
        if(event == EventField.MINE){
            callbacks.forEach{ it(EventBoard.LOSE)}
        }else if(goalAchieved() ){
            callbacks.forEach { it(EventBoard.WIN) }
        }

    }

    fun forEachFields(callback: (Field) -> Unit){
        fields.forEach(){ line -> line.forEach(callback)
        }
    }

    fun onEvent(callback: (EventBoard) -> Unit){
        callbacks.add(callback)
    }

    fun restart(){
        forEachFields { it.restart() }
        drawMines()

    }


}