package com.mtsapps.chatbuddy.ui.historyfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mtsapps.chatbuddy.databinding.FragmentHistoryBinding
import com.mtsapps.chatbuddy.utils.AdaptiveItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private var _binding : FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val historyViewModel: HistoryViewModel by viewModels()
    private val historyAdapter : HistoryAdapter by lazy { HistoryAdapter(historyViewModel,requireContext(),layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyAdapter
            binding.rvHistory.addItemDecoration(AdaptiveItemDecoration())
        }
        binding.rvHistory.adapter?.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.rvHistory.layoutManager?.scrollToPosition(historyAdapter.itemCount - 1)
            }
        })

        binding.materialToolbar2.setNavigationOnClickListener {
            findNavController().popBackStack()
        }



        getChats()
    }

    private fun getChats(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                historyViewModel.viewState.collectLatest {
                    if (it.chats?.isEmpty() == true){
                        binding.historyEmptyText.visibility = View.VISIBLE
                    }else{
                        binding.historyEmptyText.visibility = View.INVISIBLE
                    }
                    historyAdapter.submitList(it.chats?.map {

                        it.copy()
                    })
                }
            }
        }
    }

}