package com.diogonmsantos.tictactoe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diogonmsantos.tictactoe.Player.*

data class Coordinate(val x: Int, val Y: Int)

enum class Player {
    X,
    O
}

class TikTakToe: ViewModel(){
    private var lastPlayer = O
    private var _plays = mutableMapOf<Coordinate, Player>()
    private val _status = MutableLiveData<String>()
    private val _terminated =  MutableLiveData<Boolean>()

    init {
        _terminated.value = false
        _status.value = "NO WINNER YET"
    }

    val status : LiveData<String>
        get() = _status

    val terminated : LiveData<Boolean>
        get() = _terminated

    fun play(coordinate: Coordinate): Player {

        if (_plays.contains(coordinate)) {
            _status.value = "Can't play in this position"
        }

        val player = alternatePlayers()

        _plays[coordinate] = player

        if(isWinner()) {
            _status.value = "Player $player wins"
            _terminated.value = true
        }

        if(isDraw()) {
            _status.value = "Draw"
            _terminated.value = true
        }

        return player
    }

    private fun alternatePlayers(): Player {
        if (lastPlayer == O) {
            lastPlayer = X
            return lastPlayer
        }

        if (lastPlayer == X) {
            lastPlayer = O
            return lastPlayer
        }

        return lastPlayer
    }

    fun isWinner(): Boolean {
        return winnerTopRow()
                || winnerMiddleRow()
                || winnerBottomRow()
                || winnerLeftCloumn()
                || winnerMiddleColumn()
                || winnerRightCloumn()
                || winnerLeftDiagonal()
                || winnerRightDiagonal()
    }

    fun reset() {
        _plays.clear()
        lastPlayer = O
    }

    fun isDraw(): Boolean {
        if(!isWinner()) {
           return _plays.size == 9
        }

        return false
    }

    fun winningCoordinates(): List<Coordinate> {
        if (!isWinner())
            return emptyList()

        if(winnerTopRow()) return listOf(Coordinate(0,2), Coordinate(1,2), Coordinate(2,2))
        if(winnerMiddleRow()) return listOf(Coordinate(0,1), Coordinate(1,1), Coordinate(2,1))
        if(winnerBottomRow()) return listOf(Coordinate(0,0), Coordinate(1,0), Coordinate(2,0))
        if(winnerLeftCloumn()) return listOf(Coordinate(0,0), Coordinate(0,1), Coordinate(0,2))
        if(winnerMiddleColumn()) return listOf(Coordinate(1,0), Coordinate(1,1), Coordinate(1,2))
        if(winnerRightCloumn()) return listOf(Coordinate(2,0), Coordinate(2,1), Coordinate(2,2))
        if(winnerLeftDiagonal()) return listOf(Coordinate(0,2), Coordinate(1, 1), Coordinate(2, 0))
        if(winnerRightDiagonal()) return listOf(Coordinate(2, 2), Coordinate(1, 1), Coordinate(0, 0))
        return emptyList()
    }

    private fun winnerTopRow() = (_plays[Coordinate(0, 2)] == lastPlayer &&
            _plays[Coordinate(1, 2)] == lastPlayer &&
            _plays[Coordinate(2, 2)] == lastPlayer)

    private fun winnerMiddleRow() = (_plays[Coordinate(0, 1)] == lastPlayer &&
            _plays[Coordinate(1, 1)] == lastPlayer &&
            _plays[Coordinate(2, 1)] == lastPlayer)

    private fun winnerBottomRow() = (_plays[Coordinate(0, 0)] == lastPlayer &&
            _plays[Coordinate(1, 0)] == lastPlayer &&
            _plays[Coordinate(2, 0)] == lastPlayer)

    private fun winnerLeftCloumn() = (_plays[Coordinate(0, 0)] == lastPlayer &&
            _plays[Coordinate(0, 1)] == lastPlayer &&
            _plays[Coordinate(0, 2)] == lastPlayer)

    private fun winnerMiddleColumn() = (_plays[Coordinate(1, 0)] == lastPlayer &&
            _plays[Coordinate(1, 1)] == lastPlayer &&
            _plays[Coordinate(1, 2)] == lastPlayer)

    private fun winnerRightCloumn() = (_plays[Coordinate(2, 0)] == lastPlayer &&
            _plays[Coordinate(2, 1)] == lastPlayer &&
            _plays[Coordinate(2, 2)] == lastPlayer)

    private fun winnerLeftDiagonal() = (_plays[Coordinate(0, 2)] == lastPlayer &&
            _plays[Coordinate(1, 1)] == lastPlayer &&
            _plays[Coordinate(2, 0)] == lastPlayer)

    private fun winnerRightDiagonal() = (_plays[Coordinate(2, 2)] == lastPlayer &&
            _plays[Coordinate(1, 1)] == lastPlayer &&
            _plays[Coordinate(0, 0)] == lastPlayer)
}

