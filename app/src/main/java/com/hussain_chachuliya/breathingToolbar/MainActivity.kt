package com.hussain_chachuliya.breathingToolbar

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.v4.app.ActivityCompat
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
    private lateinit var snappy: Snappy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        snappy = Snappy(this, colorArray)
        // Optional : Default is 1000ms
        snappy.setDuration(1500)

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

        askPermissions()
    }

    private fun askPermissions() {
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) {

            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName"))
            ActivityCompat.startActivityForResult(this, intent, 1234, null)
        } else setStatusBarText()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            1234 -> {
                if (Build.VERSION.SDK_INT >= 23 && Settings.canDrawOverlays(this)) {
                    setStatusBarText()
                }
            }
        }
    }

    private fun setStatusBarText() {
        var statusBarHeight = 0
        val resourceId = resources.getIdentifier(
                "status_bar_height", "dimen", "android")
        if (resourceId > 0) statusBarHeight = resources.getDimensionPixelSize(resourceId)

        snappy
                .showStatusBarText(true)
                .setStatusBarText("Loading...")
                .setStatusBarHeight(statusBarHeight)
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
