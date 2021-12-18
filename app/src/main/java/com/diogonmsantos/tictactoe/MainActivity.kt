package com.diogonmsantos.tictactoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.diogonmsantos.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var gameViewModel : TikTakToe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gameViewModel = ViewModelProvider(this).get(TikTakToe::class.java)

        gameViewModel.status.observe(this, { binding.status.text = it })
        gameViewModel.terminated.observe(this, { binding.reset.isEnabled = it })

        binding.reset.setOnClickListener { reset() }
        binding.Cell00.setOnClickListener { cellClick(binding.Cell00) }
        binding.Cell01.setOnClickListener { cellClick(binding.Cell01) }
        binding.Cell02.setOnClickListener { cellClick(binding.Cell02) }
        binding.Cell10.setOnClickListener { cellClick(binding.Cell10) }
        binding.Cell11.setOnClickListener { cellClick(binding.Cell11) }
        binding.Cell12.setOnClickListener { cellClick(binding.Cell12) }
        binding.Cell20.setOnClickListener { cellClick(binding.Cell20) }
        binding.Cell21.setOnClickListener { cellClick(binding.Cell21) }
        binding.Cell22.setOnClickListener { cellClick(binding.Cell22) }
    }

    private fun reset() {
        gameViewModel.reset()

        val context = binding.root.context
        val packageName = context.packageName

        for(x:Int in 0..2) {
            for (y: Int in 0..2) {
                val cellId = context.resources.getIdentifier("Cell_${x}_${y}", "id", packageName)
                val cell = findViewById<TextView>(cellId)
                cell.text = " "
                cell.setTextColor(Color.GRAY);
            }
        }

        binding.reset.isEnabled = false
    }

    private fun cellClick(cell: View) {
        if(gameViewModel.isWinner()) {
            return
        }

        val gameCell = cell as TextView
        val idParts = gameCell.tag.toString().split("_")
        val x = idParts[0].toInt()
        val y = idParts[1].toInt()
        val player: Player = gameViewModel.play(Coordinate(x, y))

        gameCell.text = player.toString()

        if(gameViewModel.isWinner()) {
            DisplayWinner()
            return
        }
    }

    private fun DisplayWinner() {
        val coordinates = gameViewModel.winningCoordinates()

        for (coordinate: Coordinate in coordinates) {
            val cellId = resources.getIdentifier(
                "Cell_${coordinate.x}_${coordinate.Y}",
                "id",
                packageName
            )

            val cell = findViewById<TextView>(cellId)
            cell.setTextColor(Color.parseColor("#00FF00"));
        }
    }
}