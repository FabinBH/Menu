package com.fabio.navigationdrawer

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentManager
import com.fabio.navigationdrawer.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window,false)
        WindowInsetsControllerCompat(window, window.decorView).hide(WindowInsetsCompat.Type.statusBars())

        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.nav_open, R.string.nav_close)
        toggle.syncState()

        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.bottom_home -> openFragment(InicioFragment())
                R.id.bottom_achado -> openFragment(AchadoFragment())
                R.id.bottom_shop -> openFragment(ShopFragment())
                R.id.bottom_moedas -> openFragment(MoedasFragment())
            }
            true
        }
        fragmentManager = supportFragmentManager
        openFragment(InicioFragment())

        binding.fab.setOnClickListener {
            Toast.makeText(this, "Cadastrar", Toast.LENGTH_SHORT).show()
        }

        onBackPressedDispatcher.addCallback(this){
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }else{
                finish()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_premios -> openFragment(PremiosFragment())
            R.id.nav_frete-> openFragment(FreteFragment())
            R.id.nav_cupons -> openFragment(CuponsFragment())
            R.id.nav_lojas -> Toast.makeText(this,"Lojas Oficiais", Toast.LENGTH_SHORT).show()
            R.id.nav_doacoes -> Toast.makeText(this,"Doações", Toast.LENGTH_SHORT).show()
            R.id.nav_moda -> Toast.makeText(this,"Moda", Toast.LENGTH_SHORT).show()
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openFragment(fragment: androidx.fragment.app.Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()
    }
}