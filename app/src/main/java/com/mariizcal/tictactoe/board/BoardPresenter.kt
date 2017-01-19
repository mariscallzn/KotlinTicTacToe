package com.mariizcal.tictactoe.board

import com.mariizcal.tictactoe.data.DataManager
import com.mariizcal.tictactoe.data.DataManager.OnGameStatusChange
import com.mariizcal.tictactoe.data.model.*

/**
 * Created by andresmariscal on 15/01/17.
 */
class BoardPresenter(viewScreen: BoardView) {
    val dataManager: DataManager = DataManager()
    val computer = AIPlayerMinimax(dataManager.board)

    val view = viewScreen

    init {
        dataManager.setOnGameStatusChangeListener(object : OnGameStatusChange {
            override fun onStatusChange(gameState: GameState) {
                view.gameStatus(gameState)
            }
        })
    }

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
        view.reloadBoard(dataManager.playerMove(computer.oppSeed, row, col))
    }

    fun computerMove() {
        val moves = computer.move()
        view.reloadBoard(dataManager.playerMove(computer.mySeed, moves!![0], moves[1]))
    }

    interface BoardView {
        fun reloadBoard(cell: Cell?)
        fun gameStatus(gameState: GameState)
        fun initGame()
    }
}