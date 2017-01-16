package com.mariizcal.tictactoe.board

import com.mariizcal.tictactoe.data.DataManager
import com.mariizcal.tictactoe.data.model.AIPlayerMinimax
import com.mariizcal.tictactoe.data.model.Board
import com.mariizcal.tictactoe.data.model.Seed

/**
 * Created by andresmariscal on 15/01/17.
 */
class BoardPresenter(viewScreen: BoardView) {
    val dataManager: DataManager = DataManager()
    val computer = AIPlayerMinimax(dataManager.board)

    val view = viewScreen

    fun selectPlayer(seed: Seed) {
        computer.setSeed(when (seed) {
            Seed.CROSS -> Seed.NOUGHT
            else -> Seed.CROSS
        })

        view.initGame()
    }

    fun initGame() {
        dataManager.initGame()
    }

    fun playerMove(row: Int, col: Int) {
        dataManager.playerMove(when (computer.mySeed) {
            Seed.CROSS -> Seed.NOUGHT
            else -> Seed.CROSS
        }, row, col)
    }

    interface BoardView {
        fun reloadBoard(board: Board)
        fun displayResult(winner: Seed)
        fun initGame()
    }
}