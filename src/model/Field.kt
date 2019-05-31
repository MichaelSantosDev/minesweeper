package model

import jdk.jfr.Event
import java.io.File

data class Field(val line: Int, val column: Int) {

    private val neighbors = ArrayList<Field>()
    private val callbacks = ArrayList<(Field, EventField) -> Unit >()

    var opened: Boolean = false
    var marked: Boolean = false
    var mined: Boolean = false

    //Read only
    val unmarked: Boolean get() = !marked
    val closed: Boolean get() = !opened
    val safe: Boolean get() = !mined
    val goalAchieved: Boolean get() = safe && opened || marked
    val numOfMinedNeighbors: Int get() = neighbors.filter { it.mined }.size
    val safeNeighbourdhood: Boolean get() =
                                    neighbors.map { it.safe }
                                    .reduce { result, safe -> result && safe }

    fun addNeighbour(neighbor: Field){
        //TO DO verificar se o vizinho jah nao esta contido na lista
        //TO DO verificar se o vizinho nao eh o proprio campo atual
        //TO DO verificar se o vizinho esta uma linha antes, depois ou na mesma
        //TO DO validar o campo
        neighbors.add(neighbor)
    }

    fun onEvent(callback: (Field, EventField)-> Unit){
        callbacks.add(callback)
    }

    fun open(){
        if(closed){
            opened = true
            if(mined){
                callbacks.forEach{ it(this, EventField.MINE) }
            }else{
                callbacks.forEach { it(this, EventField.OPEN)}
                neighbors.filter {
                    it.closed && it.safe && safeNeighbourdhood }
                        .forEach{it.open()}
            }
        }
    }

    fun switchMark(){
            if(closed){
                marked = !marked
                val event = if(marked) EventField.MARK else EventField.UNMARK
                callbacks.forEach{ it(this, event) }
            }
    }

    fun mine(){
        mined = true
    }

    fun restart(){
        opened = false
        mined = false
        marked = false
        callbacks.forEach{it(this, EventField.RESTART)}
    }




}