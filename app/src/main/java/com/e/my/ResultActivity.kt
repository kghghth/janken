@file:Suppress("DEPRECATION")

package com.e.my

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_result.*

@Suppress("DEPRECATION")
class ResultActivity : AppCompatActivity() {

    val gu = 0
    val choki = 1
    val pa = 2

    private fun getHand(): Int {
        var hand = (Math.random() * 3).toInt()
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val gameCount = pref.getInt("GAME_COUNT", 0)
        val winnigStreakCount = pref.getInt("WINNING_STREAK_COUNT", 0)
        val lastMyHamd = pref.getInt("LAST_MY_HAND", 0)
        val lastComHand = pref.getInt("lAST_COM_HAND", 0)
        val beforelastComHand = pref.getInt("BEFORE_LAST_COM_HAND", 0)
        val gameResult = pref.getInt("GAME_RESULT", -1)


        if (gameCount == 1) {
            if (gameResult == 2) {
                // 前回の勝負が1回目で、コンピュータが勝った場合、
                // コンピュータは次に出す手を変える
                while (lastComHand == hand) {
                    hand = (Math.random() * 3).toInt()
                }
            } else if (gameResult == 1) {
                // 前回の勝負が1回目で、コンピュータが負けた場合
                // 相手の出した手に勝つ手を出す
                hand = (lastMyHamd - 1 + 3) % 3
            }
        } else if (winnigStreakCount > 0) {
            if (beforelastComHand == lastComHand) {
                //同じ手で連勝した場合は手を変える
                while (lastComHand == hand) {
                    hand = (Math.random() * 3).toInt()
                }
            }
        }
        return hand
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)


        val id = intent.getIntExtra("MY_HAND", 0)

        val myHand: Int
        myHand =
            when (id) {
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
        val comHand = getHand()
        when (comHand) {
            gu -> comHandImage.setImageResource(R.drawable.com_gu)
            choki -> comHandImage.setImageResource(R.drawable.com_choki)
            pa -> comHandImage.setImageResource(R.drawable.com_pa)
        }

        // 勝敗を判定する
        val gameResuIt = (comHand - myHand + 3) % 3
        when (gameResuIt) {
            0 -> resultLabel.setText(R.string.result_draw)  // 引き分け
            1 -> resultLabel.setText(R.string.result_win)   // 勝った場合
            2 -> resultLabel.setText(R.string.result_lose)  // 負けた場合
        }
        backButton.setOnClickListener { finish() }

        //じゃんけんの結果を保存する
        saveDate(myHand, comHand, gameResuIt)
    }

    private fun saveDate(myHand: Int, comHand: Int, gameResult: Int) {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val gameCount = pref.getInt("GAME_COUNT", 0)
        val winningStreakCount = pref.getInt("WINNING_STREAk_COUNT", 0)
        val lastComHand = pref.getInt("lAST_COM_HAND", 0)
        val lastGameResult = pref.getInt("GAME_RESULT", -1)

        val edtWinningStreakCount: Int =
            when {
                lastGameResult == 2 && gameResult == 2 ->
                    winningStreakCount + 1
                else ->
                    0
            }
        val editor = pref.edit()
        editor.putInt("GAME_COUNT", gameCount + 1)
            .putInt("WINNING_STREAK_COUNT", edtWinningStreakCount)
            .putInt("LAST_MY_HAND", myHand)
            .putInt("LAST_COM_HAND", comHand)
            .putInt("BEFFORE_LAST_COM_HAND", lastComHand)
            .putInt("GAME_RESULT", gameResult)
            .apply()


    }

//    fun saveDate(myHand: Int, comHand: Int, gameResult: Int) {
//        val pref = PreferenceManager.getDefaultSharedPreferences(this)
//        val gameCount = pref.getInt("GAME_COUNT", 0)
//        val winningStreakCount = pref.getInt("WINNING_STREAk_COUNT", 0)
//        val lastComHand = pref.getInt("lAST_COM_HAND", 0)
//        val lastGameResult = pref.getInt("GAME_RESULT", -1)
//
//        val edtWinningStreakCount: Int =
//            when {
//                lastGameResult == 2 && gameResult == 2 ->
//                    winningStreakCount + 1
//                else ->
//                    0
//            }
//        val editor = pref.edit()
//        editor.putInt("GAME_COUNT", gameCount + 1)
//            .putInt("WINNING_STREAK_COUNT", edtWinningStreakCount)
//            .putInt("LAST_MY_HAND", myHand)
//            .putInt("LAST_COM_HAND", comHand)
//            .putInt("BEFFORE_LAST_COM_HAND", lastComHand)
//            .putInt("GAME_RESULT", gameResult)
//            .apply()
//    }
}