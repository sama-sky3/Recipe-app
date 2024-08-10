package com.example.recipeappproject

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.recipeappproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var  fragmentManger : FragmentManager
    lateinit var transaction: FragmentTransaction
    val SETTING_PREFRENCE = "com.example.sharedstorageapplication"
    lateinit var editor : SharedPreferences.Editor
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_favourite, R.id.navigation_search
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration) // handles 7owar el back btn
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        when(item.itemId) {
            R.id.sign_out -> {
                // TODO cont
                val intent = Intent(this, AuthActivity::class.java)
                intent.putExtra("skipSplash", true)

                sharedPreferences =getSharedPreferences(SETTING_PREFRENCE,
                    MODE_PRIVATE
                )
                with(sharedPreferences.edit()) {
                    clear()
                    apply()
                }
                startActivity(intent)
                finish()
                return true
            }
            R.id.about_us -> {
                navController.navigate(R.id.aboutUsFragment)
                return true
            }
            else -> return false
        }
    }

}