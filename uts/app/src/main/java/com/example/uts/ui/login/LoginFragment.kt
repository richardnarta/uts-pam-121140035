package com.example.uts.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.uts.LoginActivity
import com.example.uts.R
import com.example.uts.data.LoginData
import com.example.uts.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding?=null
    private val binding get() = _binding!!

    private lateinit var etName: EditText
    private lateinit var etPass: EditText
    private lateinit var loginButton: Button
    private lateinit var registrationButton: TextView

    private lateinit var savedPass:String
    private lateinit var savedUsername: String

    private lateinit var dataStore : LoginData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        etName = binding.etUsername
        etPass = binding.etPass
        loginButton = binding.btnLogin
        registrationButton = binding.register

        dataStore = (activity as LoginActivity).loginData
        buttonLogin()

        buttonRegistration()

        return root
    }

    private fun buttonLogin(){
        lifecycleScope.launch {
            dataStore.getUsername().collect {
                savedUsername = it
            }
        }

        lifecycleScope.launch {
            dataStore.getUserPass().collect {
                savedPass = it
            }
        }

        loginButton.setOnClickListener {
            if (etName.text.toString() == savedUsername && etPass.text.toString() == savedPass && savedUsername!="" && savedPass!=""){
                lifecycleScope.launch {
                    dataStore.storeLogin(true)
                }
                (activity as LoginActivity).goToMainActivity()
            }else{
                Toast.makeText(activity,"Username / Password Invalid!!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun buttonRegistration(){
        registrationButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionNavigationLoginToNavigationRegister())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}