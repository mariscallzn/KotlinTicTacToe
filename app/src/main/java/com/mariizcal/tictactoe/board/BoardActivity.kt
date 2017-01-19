package com.mariizcal.tictactoe.board

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import com.mariizcal.tictactoe.R
import com.mariizcal.tictactoe.data.model.Cell
import com.mariizcal.tictactoe.data.model.GameState
import com.mariizcal.tictactoe.data.model.Seed
import com.mariizcal.tictactoe.utils.Animations
import kotlinx.android.synthetic.main.activity_board.*

class BoardActivity : AppCompatActivity(), BoardPresenter.BoardView {
    val presenter = BoardPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        choseSeed()

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

        Handler().postDelayed({
            presenter.computerMove()
        }, 500)
    })

    override fun reloadBoard(cell: Cell?) {
        val unwrapCell = cell ?: Cell(-1, -1)

        val sectionView: View? = when {
            unwrapCell.row == 0 && unwrapCell.col == 0 -> {
                section_top_start.setImageDrawable(when (unwrapCell.content) {
                    Seed.CROSS -> ContextCompat.getDrawable(this, R.drawable.ic_cross)
                    else -> ContextCompat.getDrawable(this, R.drawable.ic_nougth)
                })
                section_top_start
            }

            unwrapCell.row == 0 && unwrapCell.col == 1 -> {
                section_top_middle.setImageDrawable(when (unwrapCell.content) {
                    Seed.CROSS -> ContextCompat.getDrawable(this, R.drawable.ic_cross)
                    else -> ContextCompat.getDrawable(this, R.drawable.ic_nougth)
                })
                section_top_middle
            }

            unwrapCell.row == 0 && unwrapCell.col == 2 -> {
                section_top_end.setImageDrawable(when (unwrapCell.content) {
                    Seed.CROSS -> ContextCompat.getDrawable(this, R.drawable.ic_cross)
                    else -> ContextCompat.getDrawable(this, R.drawable.ic_nougth)
                })
                section_top_end
            }

            unwrapCell.row == 1 && unwrapCell.col == 0 -> {
                section_middle_start.setImageDrawable(when (unwrapCell.content) {
                    Seed.CROSS -> ContextCompat.getDrawable(this, R.drawable.ic_cross)
                    else -> ContextCompat.getDrawable(this, R.drawable.ic_nougth)
                })
                section_middle_start
            }

            unwrapCell.row == 1 && unwrapCell.col == 1 -> {
                section_middle_middle.setImageDrawable(when (unwrapCell.content) {
                    Seed.CROSS -> ContextCompat.getDrawable(this, R.drawable.ic_cross)
                    else -> ContextCompat.getDrawable(this, R.drawable.ic_nougth)
                })
                section_middle_middle
            }

            unwrapCell.row == 1 && unwrapCell.col == 2 -> {
                section_middle_end.setImageDrawable(when (unwrapCell.content) {
                    Seed.CROSS -> ContextCompat.getDrawable(this, R.drawable.ic_cross)
                    else -> ContextCompat.getDrawable(this, R.drawable.ic_nougth)
                })
                section_middle_end
            }

            unwrapCell.row == 2 && unwrapCell.col == 0 -> {
                section_bottom_start.setImageDrawable(when (unwrapCell.content) {
                    Seed.CROSS -> ContextCompat.getDrawable(this, R.drawable.ic_cross)
                    else -> ContextCompat.getDrawable(this, R.drawable.ic_nougth)
                })
                section_bottom_start
            }

            unwrapCell.row == 2 && unwrapCell.col == 1 -> {
                section_bottom_middle.setImageDrawable(when (unwrapCell.content) {
                    Seed.CROSS -> ContextCompat.getDrawable(this, R.drawable.ic_cross)
                    else -> ContextCompat.getDrawable(this, R.drawable.ic_nougth)
                })
                section_bottom_middle
            }

            unwrapCell.row == 2 && unwrapCell.col == 2 -> {
                section_bottom_end.setImageDrawable(when (unwrapCell.content) {
                    Seed.CROSS -> ContextCompat.getDrawable(this, R.drawable.ic_cross)
                    else -> ContextCompat.getDrawable(this, R.drawable.ic_nougth)
                })
                section_bottom_end
            }

            unwrapCell.row == -1 && unwrapCell.col == -1 -> {
                Log.d("BoardActivity", "Invalid position")
                return
            }
            else -> {
                return
            }
        }

        sectionView?.isEnabled = false
        sectionView?.animation = Animations.fadeIn(object : AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {
                sectionView?.visibility = View.VISIBLE
            }

            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
    }

    override fun initGame() {
        presenter.initGame()
    }

    override fun gameStatus(gameState: GameState) {
        when (gameState) {
            GameState.PLAYING -> return
            GameState.DRAW ->
                Snackbar.make(activity_board, "It's a Draw!", Snackbar.LENGTH_LONG).show()
            GameState.CROSS_WON -> {
                Snackbar.make(activity_board, "cross has won!", Snackbar.LENGTH_LONG).show()
            }
            GameState.NOUGHT_WON -> {
                Snackbar.make(activity_board, "nought has won!", Snackbar.LENGTH_LONG).show()
            }
        }

        AlertDialog.Builder(this).setMessage("Restart?")
                .setPositiveButton("YES ", { dialogInterface, which ->
                    restartGame()
                })
                .setNegativeButton("NO", { dialogInterface, which ->
                    finish()
                })
                .setCancelable(false)
                .show()
    }

    private fun restartGame() {
        section_top_start.setImageDrawable(null)
        section_top_middle.setImageDrawable(null)
        section_top_end.setImageDrawable(null)
        section_middle_start.setImageDrawable(null)
        section_middle_middle.setImageDrawable(null)
        section_middle_end.setImageDrawable(null)
        section_bottom_start.setImageDrawable(null)
        section_bottom_middle.setImageDrawable(null)
        section_bottom_end.setImageDrawable(null)

        section_top_start.isEnabled = true
        section_top_middle.isEnabled = true
        section_top_end.isEnabled = true
        section_middle_start.isEnabled = true
        section_middle_middle.isEnabled = true
        section_middle_end.isEnabled = true
        section_bottom_start.isEnabled = true
        section_bottom_middle.isEnabled = true
        section_bottom_end.isEnabled = true

        choseSeed()
    }

    fun choseSeed() {
        AlertDialog.Builder(this).setMessage("Select a player, X always starts")
                .setPositiveButton("   X   ", { dialogInterface, which ->
                    presenter.selectPlayer(Seed.CROSS)
                })
                .setNegativeButton("   O   ", { dialogInterface, which ->
                    presenter.selectPlayer(Seed.NOUGHT)
                })
                .setCancelable(false)
                .show()
    }
}
