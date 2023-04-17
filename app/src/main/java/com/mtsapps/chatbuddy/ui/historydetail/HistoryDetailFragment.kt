package com.mtsapps.chatbuddy.ui.historydetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mtsapps.chatbuddy.R
import com.mtsapps.chatbuddy.databinding.FragmentHistoryDetailBinding
import com.mtsapps.chatbuddy.databinding.FragmentHomeBinding
import com.mtsapps.chatbuddy.ui.homefragment.HomeAdapter
import com.mtsapps.chatbuddy.ui.homefragment.HomeFragmentViewModel
import com.mtsapps.chatbuddy.utils.AdaptiveItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryDetailFragment : Fragment() {
    private var _binding: FragmentHistoryDetailBinding? = null
    private val binding get() = _binding!!
    private val bundle : HistoryDetailFragmentArgs by navArgs()

    //private val historyDetailViewModel: HistoryDetailViewModel by viewModels()
    private val historyDetailAdapter: HistoryDetailAdapter by lazy { HistoryDetailAdapter(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryDetailBinding.inflate(layoutInflater)
        Log.e("detail","${bundle.chat}")

        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvHistoryDetail.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyDetailAdapter
            binding.rvHistoryDetail.addItemDecoration(AdaptiveItemDecoration())
        }
        historyDetailAdapter.submitList(bundle.chat.messages)
    }


}