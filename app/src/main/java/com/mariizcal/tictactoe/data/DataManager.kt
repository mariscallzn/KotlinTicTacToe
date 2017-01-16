package com.mariizcal.tictactoe.data

import com.mariizcal.tictactoe.data.model.AIPlayerMinimax
import com.mariizcal.tictactoe.data.model.Board
import com.mariizcal.tictactoe.data.model.GameState
import com.mariizcal.tictactoe.data.model.Seed

/**
 * Created by andresmariscal on 14/01/17.
 */

class DataManager {

    var currentState = GameState.PLAYING
    var currentPlayer = Seed.CROSS

    val board = Board()

    fun initGame() {
        board.init()
        currentState = GameState.PLAYING
        currentPlayer = Seed.CROSS
    }

    fun updateGame(player: Seed) {
        //TODO Here we need some callback to notify whether the gameState for each move
        if (board.hasWon(player)) {
            currentState = when (player) {
                Seed.CROSS -> GameState.CROSS_WON
                else -> GameState.NOUGHT_WON
            }
        } else if (board.isDraw()) {
            currentState = GameState.DRAW
        }
    }

    fun playerMove(player: Seed, row: Int, col: Int) {
        currentPlayer = player
        if (row >= 0 && row < board.ROW && col >= 0 && col < board.COL
                && board.cells[row][col]?.content == Seed.EMPTY) {
            board.cells[row][col]?.content = player
            board.currentRow = row
            board.currentCol = col
            //TODO Reload Tic Tac Toe
        } else {
            //TODO Notify error
        }

    }
}