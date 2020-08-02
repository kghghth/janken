package com.e.my

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    val gu = 0
    val choki = 1
    val pa = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val id = intent.getIntExtra("MY_HAND",0)

        val myHand: Int
        myHand = when(id) {
            R.id.gu -> {
                myHandImage.setImageResource(R.drawable.gu)
                gu
            }
            R.id.choki -> {
                myHandImage.setImageResource(R.drawable.choki)
                choki
            }
            R.id.pa -> {
                myHandImage.setImageResource(R.drawable.pa)
                pa
            }
            else -> gu
        }


        // コンピュータの手を決める
        val comHand = (Math.random() * 3 ).toInt()
        when(comHand) {
            gu -> comHandImage.setResource(R.drawable.com_gu)
            choki -> comHandImage.setResource(R.drawable.com_choki)
            pa -> comHandImage.setResource(R.drawable.com_pa)
        }

        // 勝敗を判定する
        val gameResult = (comHand - myHand +  3) % 3
        when(gameResult) {
            0 -> resultLabel.setText(R.string.result_draw)  // 引き分け
            1 -> resultLabel.setText(R.string.result_win)   // 勝った場合
            2 -> resultLabel.setText(R.string.result_lose)  // 負けた場合
        }
        backButton.setOnClickListener { finish()}
    }
}