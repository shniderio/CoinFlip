package com.example.coinflip

import android.animation.Animator
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.ceil
import kotlin.random.Random

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

        val scale = resources.displayMetrics.density
        ivCoin.cameraDistance = 8000 * scale

        onCoinTap()
    }

    private fun onCoinTap() {
        ivCoin.setOnClickListener {
            val isHeadsResult = (1..2).random() == 1
            if (isHeadsResult) {
                headsCount++
                flipCoin(R.drawable.heads)
            } else {
                tailsCount++
                flipCoin(R.drawable.tails)
            }
        }
    }

    private fun updateScores() {
        "Heads: $headsCount".also { tvHeads.text = it }
        "Tails: $tailsCount".also { tvTails.text = it }
        "Total: ${headsCount + tailsCount}".also { tvTotal.text = it }
    }

    private fun flipCoin(finalImageId: Int) {
        ivCoin.isClickable = false

        val currentRotation = ivCoin.rotationY
        val targetSpins = (ceil(currentRotation / 360f) * 360f) + (5 * 360f)
        val finalAngle = if (finalImageId == R.drawable.heads) {
            targetSpins
        } else {
            targetSpins + 180f
        }

        val randomZRotation = Random.nextFloat() * 720f + 360f

        ivCoin.animate().apply {
            duration = 2000
            rotationY(finalAngle)
            rotationBy(randomZRotation)


            setUpdateListener { animation ->
                val fraction = animation.animatedFraction
                val scale = if (fraction <= 0.5f) {
                    1.0f + (0.2f * (fraction / 0.5f))
                } else {
                    1.2f - (0.2f * ((fraction - 0.5f) / 0.5f))
                }
                ivCoin.scaleY = scale

                val yRotation = ivCoin.rotationY
                if (yRotation % 360 > 90 && yRotation % 360 < 270) {
                    ivCoin.setImageResource(R.drawable.tails)
                    ivCoin.scaleX = -scale
                } else {
                    ivCoin.setImageResource(R.drawable.heads)
                    ivCoin.scaleX = scale
                }
            }

            setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {}
                override fun onAnimationCancel(p0: Animator) {}
                override fun onAnimationRepeat(p0: Animator) {}

                override fun onAnimationEnd(p0: Animator) {
                    ivCoin.scaleY = 1f
                    ivCoin.scaleX = if (finalImageId == R.drawable.tails) -1f else 1f
                    ivCoin.setImageResource(finalImageId)

                    updateScores()
                    ivCoin.isClickable = true
                }
            })
        }.start()
    }
}
