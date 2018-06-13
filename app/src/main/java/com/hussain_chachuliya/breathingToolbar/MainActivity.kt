package com.hussain_chachuliya.breathingToolbar

import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.hussain_chachuliya.snappy.Snappy


class MainActivity : AppCompatActivity() {
    lateinit var str: SwipeRefreshLayout
    private val colorArray: Array<Int> = arrayOf(
            R.color.color_a,
            R.color.color_b,
            R.color.color_c,
            R.color.color_d,
            R.color.color_e,
            R.color.color_f,
            R.color.color_g
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val snappy = Snappy(this, colorArray)
        str = findViewById(R.id.str)
        str.setOnRefreshListener {
            snappy.startBreathing(toolbar)
            val h = Handler()
            h.postDelayed({
                Toast.makeText(this@MainActivity, "＼(^o^)／", Toast.LENGTH_SHORT).show()
                snappy.stopBreathing(toolbar)
                str.isRefreshing = false
            }, 7000)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.items, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_item1 -> {
                Toast.makeText(this, "Item1 Clicked", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
