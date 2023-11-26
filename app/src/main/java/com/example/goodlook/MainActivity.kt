package com.example.goodlook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.goodlook.databinding.ActivityMainBinding
import com.ismaeldivita.chipnavigation.ChipNavigationBar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val bottomNavView: BottomNavigationView = findViewById(R.id.bottomNavView)

        bottomNavView.setupWithNavController(navController)

        // Optional: Customize behavior if needed
        /*bottomNavView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.favorFragment -> {
                    navController.navigate(R.id.favorFragment)
                    true
                }
                R.id.recomFrag -> {
                    navController.navigate(R.id.recomFrag)
                    true
                }
                R.id.korzinFrag -> {
                    navController.navigate(R.id.korzinFrag)
                    true
                }
                else -> false
            }
        }*/
    }
}
