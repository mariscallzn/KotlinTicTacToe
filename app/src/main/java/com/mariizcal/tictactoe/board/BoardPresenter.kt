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

    var computerFirst = false

    init {
        dataManager.setOnGameStatusChangeListener(object : OnGameStatusChange {
            override fun onStatusChange(gameState: GameState) {
                view.gameStatus(gameState)
            }
        })
    }

    fun selectPlayer(seed: Seed) {
        computer.setSeed(when (seed) {
            Seed.CROSS -> {
                computerFirst = false
                Seed.NOUGHT
            }
            else -> {
                computerFirst = true
                Seed.CROSS
            }
        })

        view.initGame()
    }

    fun initGame() {
        dataManager.initGame()
        if(computerFirst) {
            view.reloadBoard(dataManager.playerMove(computer.mySeed, 0, 0))
        }
    }

    fun playerMove(row: Int, col: Int) {
        view.reloadBoard(dataManager.playerMove(computer.oppSeed, row, col))
    }

    fun computerMove() {
        val moves = computer.move()
        view.reloadBoard(dataManager.playerMove(computer.mySeed, moves!![0], moves[1]))
        view.onComputerMoved()
    }

    interface BoardView {
        fun reloadBoard(cell: Cell?)
        fun gameStatus(gameState: GameState)
        fun initGame()
        fun onComputerMoved()
    }
}