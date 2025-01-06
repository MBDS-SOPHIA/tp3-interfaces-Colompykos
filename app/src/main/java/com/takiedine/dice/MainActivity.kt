package com.takiedine.dice

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.animation.BounceInterpolator
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val targetNumberEditText: EditText = findViewById(R.id.targetNumber)

        targetNumberEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    rollDice()
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    @SuppressLint("SetTextI18n")
    private fun rollDice() {
        val dice1 = Dice(6)
        val dice2 = Dice(6)
        val diceRoll1 = dice1.roll()
        val diceRoll2 = dice2.roll()

        val diceImage1: ImageView = findViewById(R.id.diceImage1)
        val diceImage2: ImageView = findViewById(R.id.diceImage2)

        diceImage1.setImageResource(getDiceDrawable(diceRoll1))
        diceImage2.setImageResource(getDiceDrawable(diceRoll2))

        val targetNumberEditText: EditText = findViewById(R.id.targetNumber)
        val targetNumber = targetNumberEditText.text.toString().toIntOrNull()

        if (targetNumber != null) {
            if (diceRoll1 + diceRoll2 == targetNumber) {
                animateDice(diceImage1, diceImage2)
                playConfettiAnimation()
                Toast.makeText(this, "Congratulations! You win!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun animateDice(dice1: ImageView, dice2: ImageView) {
        val animator1 = ObjectAnimator.ofFloat(dice1, "translationY", 0f, -100f).apply {
            duration = 500
            interpolator = BounceInterpolator()
            repeatCount = 2
            repeatMode = ObjectAnimator.REVERSE
        }

        val animator2 = ObjectAnimator.ofFloat(dice2, "translationY", 0f, -100f).apply {
            duration = 500
            interpolator = BounceInterpolator()
            repeatCount = 2
            repeatMode = ObjectAnimator.REVERSE
        }

        animator1.start()
        animator2.start()
    }

    private fun playConfettiAnimation() {
        val confettiAnimation: LottieAnimationView = findViewById(R.id.confettiAnimation)
        confettiAnimation.visibility = LottieAnimationView.VISIBLE
        confettiAnimation.playAnimation()
    }

    private fun getDiceDrawable(value: Int): Int {
        return when (value) {
            1 -> R.drawable.dice1
            2 -> R.drawable.dice2
            3 -> R.drawable.dice3
            4 -> R.drawable.dice4
            5 -> R.drawable.dice5
            6 -> R.drawable.dice6
            else -> R.drawable.dice1
        }
    }
}
