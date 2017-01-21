package com.mariizcal.tictactoe.entry

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mariizcal.tictactoe.R
import com.mariizcal.tictactoe.board.BoardActivity
import com.mariizcal.tictactoe.data.model.Seed
import kotlinx.android.synthetic.main.activity_entry.*

/**
 * Created by andresmariscal on 21/01/17.
 */
class EntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        val intent = Intent(this, BoardActivity::class.java)

        button_cross.setOnClickListener {
            intent.putExtra("side",Seed.CROSS.ordinal)
            startActivity(intent)
        }

        button_nought.setOnClickListener {
            intent.putExtra("side",Seed.NOUGHT.ordinal)
            startActivity(intent)
        }


    }
}