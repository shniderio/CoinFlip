package com.example.coinflip

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var ivCoin: ImageView
    private lateinit var tvHeads: TextView
    private lateinit var tvTails: TextView
    private lateinit var tvTotal: TextView
    private var headsCount = 0
    private var tailsCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ivCoin = findViewById(R.id.iv_coin)
        tvHeads = findViewById(R.id.tv_heads)
        tvTails = findViewById(R.id.tv_tails)
        tvTotal = findViewById(R.id.tv_total)

        onCoinTap()
    }

    private fun onCoinTap() {
        ivCoin.setOnClickListener {
            val randomNumber = (1..2).random()
            if (randomNumber == 1) {
                flipCoin(R.drawable.heads)
                headsCount++
            } else {
                flipCoin(R.drawable.tails)
                tailsCount++
            }
            updateScores()
        }
    }

    private fun updateScores() {
        "Heads: $headsCount".also { tvHeads.text = it }
        "Tails: $tailsCount".also { tvTails.text = it }
        "Total: ${headsCount + tailsCount}".also { tvTotal.text = it }
    }

    private fun flipCoin(imageId: Int) {
        ivCoin.animate().apply {
            duration = 750
//            val variationOfLandUp = listOf(-180, -135, -90, -45, 45, 90, 135, 180).random()
            rotationYBy(1080f)
            rotationXBy(540f)
//            rotationXBy(variationOfLandUp.toFloat())
            scaleX(1.2f)
            scaleY(1.2f)
            ivCoin.isClickable = false
        }.withEndAction {
            ivCoin.setImageResource(imageId)
            ivCoin.animate().apply {
                duration = 750
                rotationXBy(1080f)
//                val variationOfLandDown = listOf(-180, -135, -90, -45, 45, 90, 135, 180).random()
                rotationYBy(540f)
//                rotationXBy(variationOfLandDown.toFloat())
                scaleX(1f)
                scaleY(1f)
            }.withEndAction {
                ivCoin.isClickable = true
            }.start()
        }.start()
    }
}
