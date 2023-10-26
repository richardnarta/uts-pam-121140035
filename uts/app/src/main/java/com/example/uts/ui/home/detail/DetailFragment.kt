package com.example.uts.ui.home.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.uts.ui.home.detail.DetailFragmentArgs
import com.example.uts.R
import com.example.uts.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    private val args : DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if(arguments!=null){
            Glide.with(requireContext()).load(args.detail[0]).into(binding.tvImage)
            binding.dbUsername.text = getString(R.string.user_name, args.detail[1], args.detail[2])
            binding.dbEmail.text = args.detail[3]
        }

        return root
    }
}