package com.mtsapps.chatbuddy.ui.homefragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mtsapps.chatbuddy.R
import com.mtsapps.chatbuddy.databinding.FragmentHomeBinding
import com.mtsapps.chatbuddy.models.ApiRequest
import com.mtsapps.chatbuddy.models.CustomMessage
import com.mtsapps.chatbuddy.utils.AdaptiveItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeFragmentViewModel by viewModels()
    private val homeAdapter: HomeAdapter by lazy { HomeAdapter(requireContext()) }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeAdapter
            binding.rvHome.addItemDecoration(AdaptiveItemDecoration())
        }
        binding.rvHome.adapter?.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.rvHome.layoutManager?.scrollToPosition(homeAdapter.itemCount - 1)
            }
        })

        binding.editTextHome.addTextChangedListener {
            binding.sendButton.isEnabled = it!!.isNotEmpty()

            binding.sendButton.isActivated = it!!.isNotEmpty()

        }

        binding.sendButton.setOnClickListener {
            val prompt = binding.editTextHome.text.toString()
            homeViewModel.sendRequest(
                ApiRequest(
                    messages = mutableListOf(
                        CustomMessage(
                            "user",
                            prompt
                        )
                    )
                )
            )
            binding.editTextHome.text.clear()
        }

        binding.floatingActionButton.setOnClickListener {

            Navigation.findNavController(it).navigate(R.id.toHistoryPage)
        }

        binding.floatingActionButton2.setOnClickListener {
            homeViewModel.addChat()
        }

        fetchMessages()
    }

    private fun fetchMessages() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.viewState.collectLatest { item ->
                    homeAdapter.submitList(item.message?.map {
                        it.copy()
                    })
                }
            }
        }
    }
}