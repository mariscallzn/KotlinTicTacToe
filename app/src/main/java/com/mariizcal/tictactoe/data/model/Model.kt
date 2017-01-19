package com.mariizcal.tictactoe.data.model

import com.mariizcal.tictactoe.utils.array2d

/**
 * Created by andresmariscal on 15/01/17.
 */
enum class Seed {
    EMPTY, CROSS, NOUGHT
}

enum class GameState {
    PLAYING, DRAW, CROSS_WON, NOUGHT_WON
}

class Cell(var row: Int, var col: Int) {
    var content: Seed

    init {
        content = Seed.EMPTY
    }

    fun clear() {
        content = Seed.EMPTY
    }
}

class Board {

    val ROW = 3
    val COL = 3

    var cells = array2d<Cell?>(ROW, COL) { null }
    var currentRow = 0
    var currentCol = 0

    val winningPatterns: Array<Int> = arrayOf(
            0b111000000, 0b000111000, 0b000000111, // rows
            0b100100100, 0b010010010, 0b001001001, // cols
            0b100010001, 0b001010100)               // diagonals


    init {
        for (row in 0 until ROW) {
            for (col in 0 until COL) {
                cells[row][col] = Cell(row, col)
            }
        }
    }

    fun init() {
        for (row in 0 until ROW) {
            for (col in 0 until COL) {
                cells[row][col]?.clear()
            }
        }
    }

    fun isDraw(): Boolean {
        for (row in 0 until ROW) {
            (0 until COL)
                    .filter { cells[row][it]?.content == Seed.EMPTY }
                    .forEach { return false }
        }
        return true
    }

    fun hasWon(thePlayer: Seed): Boolean {
        var pattern = 0b000000000
        for (row in 0 until ROW) {
            (0 until COL)
                    .asSequence()
                    .filter { cells[row][it]?.content == thePlayer }
                    .forEach { pattern = pattern or (1 shl (row * COL + it)) }
        }

        winningPatterns
                .asSequence()
                .filter { (pattern and it) == it }
                .forEach { return true }

        return false
    }
}

abstract class AIPLayer(board: Board) {
    val ROW = board.ROW
    val COL = board.COL

    var cells = board.cells

    var mySeed: Seed
    var oppSeed: Seed

    init {
        mySeed = Seed.EMPTY
        oppSeed = Seed.EMPTY
    }

    fun setSeed(seed: Seed) {
        mySeed = seed
        oppSeed = when (mySeed) {
            Seed.CROSS -> Seed.NOUGHT
            else -> Seed.CROSS
        }
    }

    abstract fun move(): Array<Int>?
}

class AIPlayerMinimax(board: Board) : AIPLayer(board) {

    override fun move(): Array<Int>? {
        val result = minimax(7, mySeed)
        return arrayOf(result[1], result[2])
    }

    fun minimax(depth: Int, player: Seed): Array<Int> {
        val nextMoves: MutableList<Array<Int>> = generateMoves()

        var bestScore = when (mySeed) {
            player -> Int.MIN_VALUE
            else -> Int.MAX_VALUE
        }
        var currentScore: Int
        var bestRow = -1
        var bestCol = -1

        if(nextMoves.isEmpty() || depth == 0){
            bestScore = evaluate()
        } else {
            nextMoves.forEach {
                cells[it[0]][it[1]]?.content = player

                if (player == mySeed){
                    currentScore = minimax(depth - 1, oppSeed)[0]
                    if(currentScore > bestScore) {
                        bestScore = currentScore
                        bestRow = it[0]
                        bestCol = it[1]
                    }
                } else {
                    currentScore = minimax(depth - 1, mySeed)[0]
                    if(currentScore < bestScore) {
                        bestScore = currentScore
                        bestRow = it[0]
                        bestCol = it[1]
                    }
                }
            cells[it[0]][it[1]]?.content = Seed.EMPTY
            }
        }
        return arrayOf(bestScore, bestRow, bestCol)
    }

    fun generateMoves(): MutableList<Array<Int>> {
        val nextMoves: MutableList<Array<Int>> = mutableListOf()

        if (hasWon(mySeed) || hasWon(oppSeed)) {
            return nextMoves
        }

        for (row in 0 until ROW) {
            (0 until COL)
                    .asSequence()
                    .filter { cells[row][it]?.content == Seed.EMPTY }
                    .forEach { nextMoves.add(arrayOf(row, it)) }
        }
        return nextMoves
    }

    val winningPatterns: Array<Int> = arrayOf(
            0b111000000, 0b000111000, 0b000000111, // rows
            0b100100100, 0b010010010, 0b001001001, // cols
            0b100010001, 0b001010100)               // diagonals

    fun hasWon(thePlayer: Seed): Boolean {
        var pattern = 0b000000000
        for (row in 0 until ROW) {
            (0 until COL)
                    .asSequence()
                    .filter { cells[row][it]?.content == thePlayer }
                    .forEach { pattern = pattern or (1 shl (row * COL + it)) }
        }

        winningPatterns
                .asSequence()
                .filter { (pattern and it) == it }
                .forEach { return true }

        return false
    }

    fun evaluate(): Int {
        var score = 0
        score += evaluateLine(0, 0, 0, 1, 0, 2)  // row 0
        score += evaluateLine(1, 0, 1, 1, 1, 2)  // row 1
        score += evaluateLine(2, 0, 2, 1, 2, 2)  // row 2
        score += evaluateLine(0, 0, 1, 0, 2, 0)  // col 0
        score += evaluateLine(0, 1, 1, 1, 2, 1)  // col 1
        score += evaluateLine(0, 2, 1, 2, 2, 2)  // col 2
        score += evaluateLine(0, 0, 1, 1, 2, 2)  // diagonal
        score += evaluateLine(0, 2, 1, 1, 2, 0)  // alternate diagonal
        return score
    }

    fun evaluateLine(row1: Int, col1: Int, row2: Int, col2: Int, row3: Int, col3: Int) : Int {
        var score = 0

        // First cell
        if(cells[row1][col1]?.content == mySeed) {
            score = 1
        } else if (cells[row1][col1]?.content == oppSeed) {
            score = -1
        }

        // Second cell
        if (cells[row2][col2]?.content == mySeed) {
            if (score == 1) {   // cell1 is mySeed
                score = 10
            } else if (score == -1) {  // cell1 is oppSeed
                return 0
            } else {  // cell1 is empty
                score = 1
            }
        } else if (cells[row2][col2]?.content == oppSeed) {
            if (score == -1) { // cell1 is oppSeed
                score = -10
            } else if (score == 1) { // cell1 is mySeed
                return 0
            } else {  // cell1 is empty
                score = -1
            }
        }

        // Third cell
        if (cells[row3][col3]?.content == mySeed) {
            if (score > 0) {  // cell1 and/or cell2 is mySeed
                score *= 10
            } else if (score < 0) {  // cell1 and/or cell2 is oppSeed
                return 0
            } else {  // cell1 and cell2 are empty
                score = 1
            }
        } else if (cells[row3][col3]?.content == oppSeed) {
            if (score < 0) {  // cell1 and/or cell2 is oppSeed
                score *= 10
            } else if (score > 1) {  // cell1 and/or cell2 is mySeed
                return 0
            } else {  // cell1 and cell2 are empty
                score = -1
            }
        }
        return score
    }

}