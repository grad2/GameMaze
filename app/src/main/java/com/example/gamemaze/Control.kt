package com.example.gamemaze

import android.graphics.Point
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast

class Control(private val gm: GameManager) {
    private val mainContext: MainActivity = gm.context as MainActivity
    private var run : Boolean = false
    val tvScore = mainContext.findViewById<TextView>(R.id.tvScore)
    init {
        move()
        additionalKeys()
    }

    private fun move(){
        val btUp = mainContext.findViewById<Button>(R.id.btUp)
        val btDown = mainContext.findViewById<Button>(R.id.btDown)
        val btLeft = mainContext.findViewById<Button>(R.id.btLeft)
        val btRight = mainContext.findViewById<Button>(R.id.btRight)

        btUp.setOnClickListener { goMove(0,-1) }
        btDown.setOnClickListener { goMove(0,1) }
        btLeft.setOnClickListener { goMove(-1,0) }
        btRight.setOnClickListener {  goMove(1,0) }

    }
    private fun goMove(diffX : Int, diffY : Int){
        var stepX = gm.player.x
        var stepY = gm.player.y

        if (run){
            while (gm.maze.canPlayerGoTo(stepX + diffX, stepY + diffY)) {
                stepX += diffX
                stepY += diffY
                bonusRemove(stepX,stepY)
                if (diffX != 0) {
                    if (gm.maze.canPlayerGoTo(stepX, stepY + 1)
                        || gm.maze.canPlayerGoTo(stepX, stepY - 1)) {
                        break
                    }
                }
                if (diffY != 0) {
                    if (gm.maze.canPlayerGoTo(stepX + 1, stepY)
                        || gm.maze.canPlayerGoTo(stepX - 1, stepY)) {
                        break
                    }
                }
            }
        }else{
            if (gm.maze.canPlayerGoTo(stepX + diffX, stepY + diffY)) {
                stepX += diffX
                stepY += diffY
            }
        }
        bonusRemove(stepX,stepY)

        gm.player.goTo(stepX, stepY)

        if (gm.player.x == 0 || gm.player.y == 0) {
            gm.create(gm.maze.size + 4)
        }

        gm.view.invalidate()
    }

    private fun bonusRemove(x :Int ,y :Int){
        for (i in gm.bonus.listPoint){
            if (x == i.x && y == i.y) {
                gm.bonus.listPoint.remove(Point(x,y))
                Toast.makeText(mainContext, "Бонус собран", Toast.LENGTH_LONG).show()
                gm.score = gm.score + 1
                tvScore.text ="Собранно: ${gm.score}"
                return
            }
        }
    }
    private fun additionalKeys(){
        val swRun = mainContext.findViewById<Switch>(R.id.swRun)
        swRun.setOnCheckedChangeListener { _, isChecked ->
            run = isChecked
        }
        val btRestart = mainContext.findViewById<Button>(R.id.btRestart)
        btRestart.setOnClickListener {
            gm.create(gm.maze.size)
            gm.view.invalidate()
        }
    }
}