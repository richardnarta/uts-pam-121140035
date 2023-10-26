package com.example.uts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.example.uts.data.LoginData
import com.example.uts.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    lateinit var loginData: LoginData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginData = LoginData(this)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.splashImage.alpha = 0f
        binding.splashText.alpha = 0f
        binding.splashText.animate().setDuration(3000).alpha(1f)
        binding.splashImage.animate().setDuration(3000).alpha(1f).withEndAction()
        {
            isLogin()
        }


    }

    private fun isLogin(){
        var isLogin:Boolean
        val notLogin=true

        this.loginData.userLoginFlow.asLiveData().observe(this){
            isLogin = it

            if(isLogin == !notLogin){
                sendToLogin()
            }else{
                sendToHome()
            }
        }
    }

    private fun sendToLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun sendToHome(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}