package com.shantanu_ramu.finalproject

import android.content.ClipData
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        var sp : SharedPreferences = getSharedPreferences("login_details",MODE_PRIVATE)
        Toast.makeText(this, "Welcome "+sp.getString("userName","User"), Toast.LENGTH_LONG).show()

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "New Actions Coming Soon", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        //code to display user details nav bar header
        val navView: NavigationView = findViewById(R.id.nav_view)
        val headerView : View = navView.getHeaderView(0)
        val navUsername : TextView = headerView.findViewById(R.id.navbarUsername)
        val navUserEmail : TextView = headerView.findViewById(R.id.navbarEmail)
        navUsername.text = sp.getString("userName","Android Studio")
        navUserEmail.text = sp.getString("userEmail","android.studio@android.com")
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        /*bLogout.setOnClickListener {
            val editor = sp.edit()
            editor.clear()
            editor.commit()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sp : SharedPreferences = getSharedPreferences("login_details",MODE_PRIVATE)
        return when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(applicationContext, "Log Out Successful", Toast.LENGTH_LONG).show()
                val editor = sp.edit()
                editor.clear()
                editor.commit()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}


