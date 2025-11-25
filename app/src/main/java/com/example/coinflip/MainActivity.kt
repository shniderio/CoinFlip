package com.example.coinflip

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
                flipCoin(R.drawable.heads, "Heads")
                headsCount++
            } else {
                flipCoin(R.drawable.tails, "Tails")
                tailsCount++
            }
            updateScores()
        }
    }

    private fun updateScores() {
        tvHeads.text = "Heads: $headsCount"
        tvTails.text = "Tails: $tailsCount"
        tvTotal.text = "Total: ${headsCount + tailsCount}"
    }

    private fun flipCoin(imageId: Int, coinSide: String) {
        ivCoin.animate().apply {
            duration = 750
            rotationYBy(1080f)
            scaleX(1.2f)
            scaleY(1.2f)
            ivCoin.isClickable = false
        }.withEndAction {
            ivCoin.setImageResource(imageId)
            Toast.makeText(this, coinSide, Toast.LENGTH_SHORT).show()
            ivCoin.animate().apply {
                duration = 750
                rotationYBy(1080f)
                scaleX(1f)
                scaleY(1f)
            }.withEndAction {
                ivCoin.isClickable = true
            }.start()
        }.start()
    }
}
