package com.example.uts.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.uts.LoginActivity
import com.example.uts.R
import com.example.uts.data.LoginData
import com.example.uts.databinding.FragmentRegistrationBinding
import kotlinx.coroutines.launch

class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding?=null
    private val binding get() = _binding!!

    private lateinit var etName: EditText
    private lateinit var etPass: EditText
    private lateinit var etGit: EditText
    private lateinit var etNIM: EditText
    private lateinit var etEmail: EditText
    private lateinit var registrationButton: Button
    private lateinit var loginButton: TextView

    private var userName = ""
    private var userPass = ""
    private var userGit = ""
    private var userNIM = ""
    private var userEmail = ""

    private lateinit var dataStore : LoginData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        etName = binding.etUsername
        etPass = binding.etPass
        etGit = binding.etGit
        etNIM = binding.etNim
        etEmail = binding.etEmail
        registrationButton = binding.btnRegistration
        loginButton = binding.login

        dataStore = (activity as LoginActivity).loginData

        buttonRegistration()

        buttonLogin()

        return root
    }

    private fun buttonRegistration(){
        registrationButton.setOnClickListener {
            userName = etName.text.toString()
            userPass = etPass.text.toString()
            userGit = etGit.text.toString()
            userNIM = etNIM.text.toString()
            userEmail = etEmail.text.toString()

            if (userName.isNotEmpty() || userPass.isNotEmpty() || userGit.isNotEmpty() ||
                userNIM.isNotEmpty() || userEmail.isNotEmpty()){
                lifecycleScope.launch{
                    dataStore.storeUser(userName, userPass, userGit, userNIM, userEmail)
                }
                lifecycleScope.launch {
                    dataStore.storeLogin(true)
                }
                (activity as LoginActivity).goToMainActivity()
            }else{
                Toast.makeText(activity,"Please Fill All Fields!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun buttonLogin(){
        loginButton.setOnClickListener {
            findNavController().navigate(RegistrationFragmentDirections.actionNavigationRegisterToNavigationLogin())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}