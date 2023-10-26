package com.example.uts.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.uts.MainActivity
import com.example.uts.data.LoginData
import com.example.uts.databinding.FragmentDashboardBinding
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    private val binding get() = _binding!!

    private lateinit var tvImage:TextView
    private lateinit var tvUsername:TextView
    private lateinit var tvGit:TextView
    private lateinit var tvNIM:TextView
    private lateinit var tvEmail:TextView
    private lateinit var logoutButton: Button

    private var regUsername=""
    private var regGit=""
    private var regNIM=""
    private var regEmail=""

    private lateinit var dataStore : LoginData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        tvImage = binding.tvImage
        tvUsername = binding.dbUsername
        tvGit = binding.dbGit
        tvNIM = binding.dbNim
        tvEmail = binding.dbEmail
        logoutButton = binding.btnLogout

        dataStore = (activity as MainActivity).loginData

        matchData()

        logoutButtonClick()

        return root
    }

    private fun matchData(){
        dataStore.userNameFlow.asLiveData().observe(activity as MainActivity){
            regUsername = it.toString()
        }
        dataStore.userGitFlow.asLiveData().observe(activity as MainActivity){
            regGit = it.toString()
        }
        dataStore.userNimFlow.asLiveData().observe(activity as MainActivity){
            regNIM = it.toString()
        }
        dataStore.userEmailFlow.asLiveData().observe(activity as MainActivity) {
            regEmail = it.toString()
        }

        tvImage.text = regUsername.first().toString().uppercase()
        tvUsername.text = regUsername
        tvGit.text = regGit
        tvNIM.text = regNIM
        tvEmail.text = regEmail
    }

    private fun logoutButtonClick(){
        logoutButton.setOnClickListener {
            lifecycleScope.launch {
                dataStore.storeLogin(false)
            }
            (activity as MainActivity).sendToLogin()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}