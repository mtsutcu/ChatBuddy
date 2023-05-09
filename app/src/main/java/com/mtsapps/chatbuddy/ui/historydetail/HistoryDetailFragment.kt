package com.mtsapps.chatbuddy.ui.historydetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mtsapps.chatbuddy.databinding.FragmentHistoryDetailBinding
import com.mtsapps.chatbuddy.utils.AdaptiveItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryDetailFragment : Fragment() {
    private var _binding: FragmentHistoryDetailBinding? = null
    private val binding get() = _binding!!
    private val bundle : HistoryDetailFragmentArgs by navArgs()

    private val historyDetailAdapter: HistoryDetailAdapter by lazy { HistoryDetailAdapter(requireContext()) }

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
        binding.materialToolbar3.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }




}