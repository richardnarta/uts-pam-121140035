package com.example.uts

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.uts.data.LoginData
import com.example.uts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var loginData: LoginData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginData = LoginData(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNav()
        supportActionBar?.setDisplayShowHomeEnabled(true);
    }

    fun getData():ArrayList<String>{
        var regUsername=""
        var regGit=""
        var regNIM=""
        var regEmail=""
        loginData.userNameFlow.asLiveData().observe(this){
            regUsername = it.toString()
        }
        loginData.userGitFlow.asLiveData().observe(this){
            regGit = it.toString()
        }
        loginData.userNimFlow.asLiveData().observe(this){
            regNIM = it.toString()
        }
        loginData.userEmailFlow.asLiveData().observe(this){
            regEmail = it.toString()
        }

        return arrayListOf(regUsername, regGit, regNIM, regEmail)
    }

    private fun setNav(){
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun sendToLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}