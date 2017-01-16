package com.mariizcal.tictactoe.board

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import com.mariizcal.tictactoe.R
import com.mariizcal.tictactoe.data.model.Board
import com.mariizcal.tictactoe.data.model.Seed
import kotlinx.android.synthetic.main.activity_board.*

class BoardActivity : AppCompatActivity(), BoardPresenter.BoardView {
    val presenter = BoardPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        AlertDialog.Builder(this).setMessage("Select a player, X always starts")
                .setPositiveButton("   X   ", { dialogInterface, which ->
                    presenter.selectPlayer(Seed.CROSS)
                })
                .setNegativeButton("   O   ", { dialogInterface, which ->
                    presenter.selectPlayer(Seed.NOUGHT)
                })
                .show()

        section_top_start.setOnClickListener(onClick)
        section_top_middle.setOnClickListener(onClick)
        section_top_end.setOnClickListener(onClick)
        section_middle_start.setOnClickListener(onClick)
        section_middle_middle.setOnClickListener(onClick)
        section_middle_end.setOnClickListener(onClick)
        section_bottom_start.setOnClickListener(onClick)
        section_bottom_middle.setOnClickListener(onClick)
        section_bottom_end.setOnClickListener(onClick)
    }

    val onClick = View.OnClickListener({
        view ->
        when (view.id) {
            section_top_start.id -> {
                Log.w("onClick", "section_top_start")
                presenter.playerMove(0, 0)
            }
            section_top_middle.id -> {
                Log.w("onClick", "section_top_middle")
                presenter.playerMove(0, 1)
            }
            section_top_end.id -> {
                Log.w("onClick", "section_top_end")
                presenter.playerMove(0, 2)
            }
            section_middle_start.id -> {
                Log.w("onClick", "section_middle_start")
                presenter.playerMove(1, 0)
            }
            section_middle_middle.id -> {
                Log.w("onClick", "section_middle_middle")
                presenter.playerMove(1, 1)
            }
            section_middle_end.id -> {
                Log.w("onClick", "section_middle_end")
                presenter.playerMove(1, 2)
            }
            section_bottom_start.id -> {
                Log.w("onClick", "section_bottom_start")
                presenter.playerMove(2, 0)
            }
            section_bottom_middle.id -> {
                Log.w("onClick", "section_bottom_middle")
                presenter.playerMove(2, 1)
            }
            section_bottom_end.id -> {
                Log.w("onClick", "section_bottom_end")
                presenter.playerMove(2, 2)
            }
        }
    })

    override fun reloadBoard(board: Board) {

    }

    override fun displayResult(winner: Seed) {

    }

    override fun initGame() {
        presenter.initGame()
    }
}
