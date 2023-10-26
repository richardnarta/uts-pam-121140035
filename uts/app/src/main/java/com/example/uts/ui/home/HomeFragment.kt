package com.example.uts.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uts.databinding.FragmentHomeBinding
import com.example.uts.network.ApiConfig
import com.example.uts.userHelper.ResponseUser
import com.example.uts.userHelper.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter:Adapter
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = Adapter(mutableListOf(), this)
        searchView = binding.search

        createRecyclerView()

        getUser()

        defineSearchView()

        recyclerOnClickListener()

        return root
    }

    private fun recyclerOnClickListener(){
        adapter.setOnClickListener(object :
            Adapter.OnClickListener{
            override fun onClick(position: Int, model: User){
                findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToNavigationDetail(
                    arrayOf(model.image ,model.firstName, model.lastName, model.email)
                ))
            }
        })
    }

    private fun defineSearchView(){
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter(newText)
                return false
            }
        })
    }

    private fun createRecyclerView(){
        val recyclerView = binding.recycleView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun getUser(){
        val client = ApiConfig.getApiService().getListUser("2")

        client.enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if(response.isSuccessful){
                    val dataUser = response.body()?.data as List<User>
                    for(data in dataUser){
                        adapter.addUser(data)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}