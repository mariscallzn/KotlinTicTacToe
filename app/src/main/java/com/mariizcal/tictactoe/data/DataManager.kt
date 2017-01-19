package com.mariizcal.tictactoe.data

import com.mariizcal.tictactoe.data.model.*

/**
 * Created by andresmariscal on 14/01/17.
 */

class DataManager {

    var currentState = GameState.PLAYING
    var currentPlayer = Seed.CROSS //Cross is the default

    val board = Board()

    var gameStatusListener: OnGameStatusChange? = null

    fun initGame() {
        board.init()
        currentState = GameState.PLAYING
        currentPlayer = Seed.CROSS
    }

    fun updateGame(player: Seed) {
        if (board.hasWon(player)) {
            currentState = when (player) {
                Seed.CROSS -> GameState.CROSS_WON
                else -> GameState.NOUGHT_WON
            }
        } else if (board.isDraw()) {
            currentState = GameState.DRAW
        }

        gameStatusListener?.onStatusChange(currentState)
    }

    fun playerMove(player: Seed, row: Int, col: Int) : Cell? {
        currentPlayer = player
        if (row >= 0 && row < board.ROW && col >= 0 && col < board.COL
                && board.cells[row][col]?.content == Seed.EMPTY) {
            board.cells[row][col]?.content = player
            board.currentRow = row
            board.currentCol = col

            val cell = Cell(row, col)
            cell.content = player

            updateGame(player)
            return cell
        } else {
            return null
        }
    }

    fun setOnGameStatusChangeListener(listener: OnGameStatusChange?){
        this.gameStatusListener = listener
    }

    public interface OnGameStatusChange {
        fun onStatusChange(gameState: GameState)
    }
}