package com.example.coinflip

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var ivCoin: ImageView

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

        onCoinTap()
    }

    private fun onCoinTap() {
        ivCoin.setOnClickListener {
            val randomNumber = (1..2).random()
            if (randomNumber == 1) {
                flipCoin(R.drawable.heads, "Heads")
            } else {
                flipCoin(R.drawable.tails, "Tails")
            }
        }
    }

    private fun flipCoin(imageId: Int, coinSide: String) {
        ivCoin.animate().apply {
            duration = 1500
            rotationYBy(2160f)
            ivCoin.isClickable = false
        }.withEndAction {
            ivCoin.setImageResource(imageId)
            Toast.makeText(this, coinSide, Toast.LENGTH_SHORT).show()
            ivCoin.isClickable = true
        }.start()
    }
}
