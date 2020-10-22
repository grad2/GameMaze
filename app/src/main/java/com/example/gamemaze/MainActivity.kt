package com.example.gamemaze

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.example.gamemaze.item.maze.MazeView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lvMap = findViewById<LinearLayout>(R.id.lvMap)

        val gameManager : GameManager = GameManager(this)
        val view = MazeView(this, gameManager)
        view.invalidate()
        lvMap.addView(view)
    }

}