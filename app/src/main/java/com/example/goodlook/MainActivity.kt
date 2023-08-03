package com.example.goodlook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar


class MainActivity : AppCompatActivity() {

    private lateinit var chipNavigationBar: ChipNavigationBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chipNavigationBar = findViewById(R.id.bottom_nav_bar)
        chipNavigationBar.setItemSelected(R.id.favorFragment, true)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FavorFragment())
            .commit()

        bottomMenu()
    }

    private fun bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(
            object : ChipNavigationBar.OnItemSelectedListener {
                override fun onItemSelected(i: Int) {
                    var fragment: Fragment? = null
                    when (i) {
                        R.id.favorFragment -> fragment = FavorFragment()
                        R.id.recomFrag -> fragment = RecomFrag()
                        R.id.korzinFrag -> fragment = KorzinFrag()
                    }
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment!!)
                        .commit()
                }
            }
        )
    }
}